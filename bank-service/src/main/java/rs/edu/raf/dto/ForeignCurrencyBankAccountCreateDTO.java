package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ForeignCurrencyBankAccountCreateDTO (
        @NotNull Long owner,
        @NotNull Long currencyId,
        @NotNull @PositiveOrZero BigDecimal maintenancePrice
) { }
