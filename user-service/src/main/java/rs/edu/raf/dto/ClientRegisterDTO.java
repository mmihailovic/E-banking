package rs.edu.raf.dto;

import jakarta.validation.constraints.Pattern;

public record ClientRegisterDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    String email,
    @Pattern(regexp = "^(\\+381|0)6\\d{7,8}$", message = "The phone number must start with +381 or 0, followed by 6 and then 7 or 8 digits!")
    String phoneNumber,
    String bankAccountNumber,

    String password,

    String code

) { }
