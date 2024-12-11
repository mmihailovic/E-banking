package rs.edu.raf.mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CreditRequestCreateDto;
import rs.edu.raf.dto.CreditRequestDTO;
import rs.edu.raf.model.credit.CreditRequest;
import rs.edu.raf.model.credit.CreditRequestStatus;
import rs.edu.raf.model.credit.CreditType;
import rs.edu.raf.repository.CurrencyRepository;
import rs.edu.raf.repository.accounts.BankAccountRepository;

@Component
@AllArgsConstructor
public class CreditRequestMapper {
    private BankAccountRepository bankAccountRepository;
    private CurrencyRepository currencyRepository;
    private CurrencyMapper currencyMapper;
    private BankAccountMapper bankAccountMapper;
    private CreditTypeMapper creditTypeMapper;

    public CreditRequest creditRequestCreateDtoToCreditRequest(CreditRequestCreateDto creditRequestCreateDto, CreditType creditType){
        CreditRequest creditRequest = new CreditRequest();

        creditRequest.setCreditType(creditType);
        creditRequest.setLoanAmount(creditRequestCreateDto.amount());
        creditRequest.setCurrency(currencyRepository.findById(creditRequestCreateDto.currencyId()).orElseThrow());
        creditRequest.setSalary(creditRequestCreateDto.salary());
        creditRequest.setLoanPurpose(creditRequestCreateDto.loanPurpose());
        creditRequest.setPhoneNumber(creditRequestCreateDto.phoneNumber());
        creditRequest.setBankAccount(bankAccountRepository.findByAccountNumber(creditRequestCreateDto.bankAccountNumber()).orElseThrow());
        creditRequest.setLoanTerm(creditRequestCreateDto.loanTerm());
        creditRequest.setPermanentEmployee(creditRequestCreateDto.permanentEmployee());
        creditRequest.setCurrentEmploymentPeriod(creditRequestCreateDto.currentEmploymentPeriod());
        creditRequest.setCreditRequestStatus(CreditRequestStatus.PENDING);

        return creditRequest;
    }

    public CreditRequestDTO creditRequestToCreditRequestDto(CreditRequest creditRequest){
        return new CreditRequestDTO(creditRequest.getId(), creditTypeMapper.creditTypeToCreditTypeDTO(creditRequest.getCreditType()),
                creditRequest.getLoanAmount(), currencyMapper.currencyToCurrencyDTO(creditRequest.getCurrency()),
                creditRequest.getLoanPurpose(), creditRequest.getSalary(), creditRequest.getPhoneNumber(),
                creditRequest.isPermanentEmployee(), creditRequest.getCurrentEmploymentPeriod(),
                creditRequest.getLoanTerm(), bankAccountMapper.bankAccountToBankAccountDTO(creditRequest.getBankAccount()),
                creditRequest.getCreditRequestStatus().toString());
    }
}
