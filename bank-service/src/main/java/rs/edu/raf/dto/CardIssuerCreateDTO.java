package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;

public record CardIssuerCreateDTO(@NotNull String name, int MII, int BIN) {
}
