package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CreditDTO;
import rs.edu.raf.dto.CreditRequestCreateDto;
import rs.edu.raf.dto.CreditRequestDTO;
import rs.edu.raf.exceptions.*;
import rs.edu.raf.mapper.CreditMapper;
import rs.edu.raf.model.accounts.BankAccount;
import rs.edu.raf.model.credit.Credit;
import rs.edu.raf.model.credit.CreditRequest;
import rs.edu.raf.mapper.CreditRequestMapper;
import rs.edu.raf.model.credit.CreditRequestStatus;
import rs.edu.raf.model.credit.CreditType;
import rs.edu.raf.repository.CreditRepository;
import rs.edu.raf.repository.CreditRequestRepository;
import rs.edu.raf.repository.CreditTypeRepository;
import rs.edu.raf.service.CreditService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRequestRepository creditRequestRepository;
    private final CreditRequestMapper creditRequestMapper;
    private final CreditRepository creditRepository;
    private final CreditTypeRepository creditTypeRepository;
    private final CreditMapper creditMapper;

    public CreditRequestDTO applyForCredit(CreditRequestCreateDto creditRequestCreateDto){
        CreditType creditType = creditTypeRepository.findById(creditRequestCreateDto.creditTypeId())
                .orElseThrow(() -> new CreditTypeNotFoundException(creditRequestCreateDto.creditTypeId()));

        int loanTerm = creditRequestCreateDto.loanTerm();
        BigDecimal loanAmount = creditRequestCreateDto.amount();

        if(loanTerm < creditType.getMinLoanTerm() || loanTerm > creditType.getMaxLoanTerm())
            throw new WrongCreditLoanTermException(creditType.getMinLoanTerm(), creditType.getMaxLoanTerm());

        if(loanAmount.compareTo(creditType.getMaxLoanAmount()) > 0)
            throw new WrongLoanAmountException(creditType.getMaxLoanAmount());

        CreditRequest creditRequest = creditRequestMapper.creditRequestCreateDtoToCreditRequest(creditRequestCreateDto, creditType);
        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequestRepository.save(creditRequest));
    }

    public CreditRequestDTO approveCreditRequest(Long id){
        CreditRequest creditRequest = creditRequestRepository.findById(id)
                .orElseThrow(()->new CreditRequestNotFoundException(id));

        creditRequest.setCreditRequestStatus(CreditRequestStatus.APPROVED);
        creditRequestRepository.save(creditRequest);
        createCredit(creditRequest);
        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequest);
    }

    public CreditRequestDTO dennyCreditRequest(Long id){
        CreditRequest creditRequest = creditRequestRepository.findById(id)
                .orElseThrow(()->new CreditRequestNotFoundException(id));

        creditRequest.setCreditRequestStatus(CreditRequestStatus.DENIED);
        creditRequestRepository.save(creditRequest);
        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequest);
    }

    public List<CreditRequestDTO> getAllCreditRequestsWithStatus(String status){
        CreditRequestStatus creditRequestStatus = CreditRequestStatus.valueOf(status);
        return creditRequestRepository.findAllByCreditRequestStatus(creditRequestStatus)
                .stream()
                .map(creditRequestMapper::creditRequestToCreditRequestDto)
                .toList();
    }

    public List<CreditRequestDTO> getAllCreditRequestForUserWithStatus(Long userId, String status){
        CreditRequestStatus creditRequestStatus = CreditRequestStatus.valueOf(status);
        return creditRequestRepository.findAllByBankAccount_OwnerAndCreditRequestStatus(userId, creditRequestStatus)
                .stream()
                .map(creditRequestMapper::creditRequestToCreditRequestDto)
                .collect(Collectors.toList());
    }


    @Override
    public void createCredit(CreditRequest creditRequest){
        Credit credit = creditMapper.creditRequestToCredit(creditRequest);
        credit.setInstallmentAmount(calculateInstallmentAmountForCredit(creditRequest));
        creditRepository.save(credit);
    }

    @Override
    public List<CreditDTO> getAllCredits() {
        return creditRepository.findAll()
                .stream()
                .map(creditMapper::creditToCreditDTO)
                .toList();
    }

    @Override
    public void processCreditInstallment() {
        List<Credit> credits = creditRepository.findAllByRemainingDebtGreaterThan(BigDecimal.ZERO);

        long now = System.currentTimeMillis();

        Long nextInstallmentDate = LocalDate.now().plusMonths(1).atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        for(Credit credit: credits) {
            if(credit.getNextInstallmentDate() <= now) {
                BankAccount bankAccount = credit.getCreditRequest().getBankAccount();
                bankAccount.setBalance(bankAccount.getBalance().subtract(credit.getInstallmentAmount()));
                bankAccount.setAvailableBalance(bankAccount.getAvailableBalance().subtract(credit.getInstallmentAmount()));
                credit.setRemainingDebt(credit.getRemainingDebt().subtract(credit.getInstallmentAmount()));
                credit.setNextInstallmentDate(nextInstallmentDate);
            }
        }
    }

    @Override
    public CreditDTO getDetailedCredit(Long id){
        return creditMapper.creditToCreditDTO(creditRepository.findById(id)
                .orElseThrow(()->new CreditNotFoundException(id)));
    }

    @Override
    public BigDecimal calculateInstallmentAmountForCredit(CreditRequest creditRequest) {
        int loanTerm = creditRequest.getLoanTerm();
        BigDecimal loanAmount = creditRequest.getLoanAmount();

        BigDecimal monthlyRate = creditRequest.getCreditType().getNominalInterestRate()
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);

        BigDecimal numerator = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(loanTerm));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(loanTerm).subtract(BigDecimal.ONE);

        BigDecimal monthlyPayment = loanAmount.multiply(numerator).divide(denominator, RoundingMode.HALF_UP);

        return monthlyPayment.setScale(2, RoundingMode.HALF_UP);
    }
}
