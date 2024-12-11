package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreditRequestCreateDto (
    @NotNull Long creditTypeId,
    @NotNull @Positive BigDecimal amount,
    @NotNull Long currencyId,
    @NotNull String loanPurpose,
    @NotNull @PositiveOrZero BigDecimal salary,
    @NotNull String phoneNumber,
    boolean permanentEmployee,
    int currentEmploymentPeriod,
    int loanTerm,
    @NotNull Long bankAccountNumber

) { }
