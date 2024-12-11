package rs.edu.raf.dto;

import java.math.BigDecimal;

public record CreditDTO(CreditRequestDTO creditRequest, Long contractDate, Long loanMaturityDate,
                        BigDecimal installmentAmount, BigDecimal remainingDebt, Long nextInstallmentDate) {
}
