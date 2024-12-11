package rs.edu.raf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CurrencyCreateDTO(@NotNull @NotEmpty String name, @NotNull @NotEmpty String code,
                                @NotNull @NotEmpty String symbol, @NotNull Long countryId) {
}
