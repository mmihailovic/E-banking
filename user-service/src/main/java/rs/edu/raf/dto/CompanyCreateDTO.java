package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;

public record CompanyCreateDTO(
        @NotNull String name,
        @NotNull String phoneNumber,
        @NotNull String address,
        @NotNull String faxNumber,
        @NotNull Integer TIN,
        @NotNull Integer registrationNumber,
        @NotNull Integer businessActivityCode,
        @NotNull Integer registryNumber
) {
}
