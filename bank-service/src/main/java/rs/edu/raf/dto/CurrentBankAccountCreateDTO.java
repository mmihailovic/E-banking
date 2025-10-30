package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CurrentBankAccountCreateDTO (
        @NotNull String JBMG,
        @NotNull String accountType,
        @PositiveOrZero BigDecimal maintenancePrice
) { }
