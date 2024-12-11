package rs.edu.raf.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.edu.raf.annotations.GeneratedCrudOperation;
import rs.edu.raf.dto.EditEmployeeDTO;
import rs.edu.raf.dto.EmployeeCreateDTO;
import rs.edu.raf.dto.EmployeeDTO;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.mapper.EmployeeMapper;
import rs.edu.raf.model.user.Employee;
import rs.edu.raf.repository.EmployeeRepository;
import rs.edu.raf.repository.RoleRepository;
import rs.edu.raf.service.EmployeeService;
import rs.edu.raf.util.JMBGValidator;

import java.util.List;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Override
    public EmployeeDTO createNewEmployee(EmployeeCreateDTO employeeCreateDTO) {

        Employee employee = employeeMapper.employeeCreateDTOtoEmployee(employeeCreateDTO);

        JMBGValidator.validateJMBG(employee.getDateOfBirth(), employee.getJMBG());

        return employeeMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO editEmployee(EditEmployeeDTO editEmployeeDTO) {

        Employee employee = employeeRepository.findById(editEmployeeDTO.id())
                .orElseThrow(() -> new UserNotFoundException("Employee with id " + editEmployeeDTO.id() + " not found!"));

        updateIfPresent(employee::setLastName, editEmployeeDTO.lastName());
        updateIfPresent(employee::setGender, editEmployeeDTO.gender());
        updateIfPresent(employee::setPhoneNumber, editEmployeeDTO.phoneNumber());
        updateIfPresent(employee::setPassword, bCryptPasswordEncoder.encode(editEmployeeDTO.password()));
        updateIfPresent(employee::setPosition, editEmployeeDTO.position());
        updateIfPresent(employee::setAddress, editEmployeeDTO.address());
        updateIfPresent(employee::setDepartment, editEmployeeDTO.department());
        employee.setActive(editEmployeeDTO.active());

        if(editEmployeeDTO.roles() != null) {
            employee.setRoles(editEmployeeDTO.roles().stream().map(role -> roleRepository.findByName(role)
                    .orElseThrow()).toList());
        }

        return employeeMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> getAllActiveEmployees() {
        return employeeRepository.findAllByActiveIsTrue().stream().map(employeeMapper::employeeToEmployeeDTO).toList();
    }

    @Override
    public EmployeeDTO findActiveEmployeeByEmail(String email) {
        return employeeMapper.employeeToEmployeeDTO(employeeRepository
                .findByEmailAndActiveIsTrue(email)
                .orElseThrow(()->new UserNotFoundException("Employee with email " + email + " not found!")));
    }

    @GeneratedCrudOperation
    @Override
    public EmployeeDTO findActiveEmployeeById(Long id){
        return employeeMapper.employeeToEmployeeDTO(employeeRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee with ID " + id + " not found!")));
    }

    private void updateIfPresent(Consumer<String> setter, String value) {
        if(StringUtils.isNotBlank(value))
            setter.accept(value);
    }
}
