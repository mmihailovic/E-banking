package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreditTypeCreateDTO(@NotNull String name, @NotNull @Positive BigDecimal nominalInterestRate,
                                  int minLoanTerm, int maxLoanTerm, @NotNull @Positive BigDecimal maxLoanAmount,
                                  BigDecimal prepayment) {
}
