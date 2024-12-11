package rs.edu.raf.dto;

import java.math.BigDecimal;

public record CreditRequestDTO(Long id, CreditTypeDTO creditType, BigDecimal loanAmount, CurrencyDTO currency,
                               String loanPurpose, BigDecimal salary, String phoneNumber, boolean permanentEmployee,
                               int currentEmploymentPeriod, int loanTerm, BankAccountDTO bankAccount,
                               String creditRequestStatus) {
}
