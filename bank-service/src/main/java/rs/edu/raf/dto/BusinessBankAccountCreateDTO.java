package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;

public record BusinessBankAccountCreateDTO(@NotNull Long companyId) {
}
