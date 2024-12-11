package rs.edu.raf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CountryCreateDTO(@NotNull @NotEmpty String name) {
}
