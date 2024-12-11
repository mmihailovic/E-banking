package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.EmployeeCreateDTO;
import rs.edu.raf.dto.EmployeeDTO;
import rs.edu.raf.model.user.Employee;
import rs.edu.raf.model.user.Role;
import rs.edu.raf.repository.CompanyRepository;
import rs.edu.raf.repository.RoleRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CompanyRepository companyRepository;
    private RoleRepository roleRepository;

    public Employee employeeCreateDTOtoEmployee(EmployeeCreateDTO employeeCreateDTO){
        Employee employee = new Employee();

        employee.setFirstName(employeeCreateDTO.firstName());
        employee.setLastName(employeeCreateDTO.lastName());
        employee.setJMBG(employeeCreateDTO.JMBG());
        employee.setDateOfBirth(employeeCreateDTO.dateOfBirth());
        employee.setGender(employeeCreateDTO.gender());
        employee.setEmail(employeeCreateDTO.email());
        employee.setPhoneNumber(employeeCreateDTO.phoneNumber());
        employee.setAddress(employeeCreateDTO.address());
        employee.setPassword(bCryptPasswordEncoder.encode(employeeCreateDTO.password()));
        employee.setPosition(employeeCreateDTO.position());
        employee.setDepartment(employeeCreateDTO.department());

        List<Role> roles = employeeCreateDTO.roles()
                .stream()
                .map(role -> roleRepository.findByName(role).orElseThrow())
                .toList();

        employee.setRoles(roles);
        employee.setActive(employeeCreateDTO.active());
        employee.setCompany(companyRepository.findById(employeeCreateDTO.companyId()).orElseThrow());

        return employee;
    }

    public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getJMBG(),
            employee.getDateOfBirth(),
            employee.getGender(),
            employee.getEmail(),
            employee.getPhoneNumber(),
            employee.getAddress(),
            employee.getPosition(),
            employee.getDepartment(),
            employee.getRoles().stream().map(Role::getName).toList(),
            employee.getCompany().getId()
        );
    }
}
