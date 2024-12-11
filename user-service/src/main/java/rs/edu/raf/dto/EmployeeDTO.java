package rs.edu.raf.dto;

import java.util.List;

public record EmployeeDTO(Long id, String firstName, String lastName, String JMBG, Long dateOfBirth, String gender,
                          String email, String phoneNumber, String address, String position, String department,
                          List<String> roles, Long companyId
)
{ }
