package rs.edu.raf.dto;

import jakarta.validation.constraints.Pattern;

public record GenerateCodeDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    String email
) { }

