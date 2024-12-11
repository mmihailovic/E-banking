package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;

public record MarketOrderCreateDTO(@NotNull String orderAction, @NotNull String ticker, @NotNull Integer quantity) {
}
