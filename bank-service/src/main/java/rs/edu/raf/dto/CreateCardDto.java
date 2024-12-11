package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateCardDto (
    @NotNull String type,
    @NotNull Long issuerId,
    @NotNull String name,
    @NotNull Long bankAccountNumber,
    @NotNull @Positive BigDecimal limit

) { }
