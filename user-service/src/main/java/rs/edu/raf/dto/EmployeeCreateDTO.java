package rs.edu.raf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record EmployeeCreateDTO (
        Long companyId,
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Only one or more letters are allowed!")
        String firstName,

        @Pattern(regexp = "^[a-zA-Z]+$", message = "Only one or more letters are allowed!")
        String lastName,
        @NotNull String JMBG,
        @NotNull Long dateOfBirth,
        @Pattern(regexp = "^[M|F]$", message = "Gender can be M or F!")
        String gender,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
        String email,
        @Pattern(regexp = "^(\\+381|0)6\\d{7,8}$", message = "The phone number must start with +381 or 0, followed by 6 and then 7 or 8 digits!")
        String phoneNumber,
        String address,
        String password,
        @NotNull
        String position,
        @NotNull
        String department,
        List<String> roles,
        boolean active
) { }
