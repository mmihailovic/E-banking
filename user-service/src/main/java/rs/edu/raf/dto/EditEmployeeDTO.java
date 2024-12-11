package rs.edu.raf.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record EditEmployeeDTO (
        Long id,
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Only one or more letters are allowed!")
        String lastName,

        @Pattern(regexp = "^[M|F]$", message = "Gender can be M or F!")
        String gender,

        @Pattern(regexp = "^(\\+381|0)6\\d{7,8}$", message = "The phone number must start with +381 or 0, followed by 6 and then 7 or 8 digits!")
        String phoneNumber,
        String address,
        String password,
        String position,
        String department,
        List<String> roles,
        boolean active,
        @PositiveOrZero
        BigDecimal dailyLimit,
        boolean approvalFlag,
        boolean supervisor
) { }
