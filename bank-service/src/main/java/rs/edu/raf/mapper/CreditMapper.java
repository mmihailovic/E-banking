package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CreditDTO;
import rs.edu.raf.model.credit.Credit;
import rs.edu.raf.model.credit.CreditRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class CreditMapper {
    private CreditRequestMapper creditRequestMapper;

    public Credit creditRequestToCredit(CreditRequest creditRequest) {
        Credit credit = new Credit();

        credit.setCreditRequest(creditRequest);
        credit.setContractDate(System.currentTimeMillis());

        long loanMaturityDate = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault())
                .plusMonths(creditRequest.getLoanTerm())
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        credit.setLoanMaturityDate(loanMaturityDate);
        credit.setRemainingDebt(creditRequest.getLoanAmount());

        long nextInstallmentDate = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault())
                .plusMonths(1)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        credit.setNextInstallmentDate(nextInstallmentDate);

        return credit;
    }

    public CreditDTO creditToCreditDTO(Credit credit) {
        return new CreditDTO(creditRequestMapper.creditRequestToCreditRequestDto(credit.getCreditRequest()),
                credit.getContractDate(), credit.getLoanMaturityDate(), credit.getInstallmentAmount(),
                credit.getRemainingDebt(), credit.getNextInstallmentDate());
    }
}
