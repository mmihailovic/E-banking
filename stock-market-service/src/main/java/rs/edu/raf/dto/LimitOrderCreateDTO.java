package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;

public record LimitOrderCreateDTO (@NotNull String orderAction, @NotNull String ticker, @NotNull Integer quantity,
                                   @NotNull Double limit) {
}
