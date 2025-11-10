package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreditTypeCreateDTO(@NotNull String name, @NotNull @Positive BigDecimal nominalInterestRate,
                                  @PositiveOrZero Long currencyId, int minLoanTerm, int maxLoanTerm,
                                  @NotNull @Positive BigDecimal maxLoanAmount, BigDecimal prepayment) {
}
