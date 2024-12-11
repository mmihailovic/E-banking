package rs.edu.raf.dto;

import java.math.BigDecimal;

public record CreditTypeDTO(Long id, String name, BigDecimal nominalInterestRate, int minLoanTerm, int maxLoanTerm,
                            BigDecimal maxLoanAmount, BigDecimal prepayment) {
}
