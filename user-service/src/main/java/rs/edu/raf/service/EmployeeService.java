package rs.edu.raf.service;

import rs.edu.raf.dto.EditEmployeeDTO;
import rs.edu.raf.dto.EmployeeCreateDTO;
import rs.edu.raf.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    /**
     * Creates a new employee.
     *
     * @param employeeCreateDTO The DTO containing information about the new employee.
     * @return The created {@link EmployeeDTO} object.
     */
    EmployeeDTO createNewEmployee(EmployeeCreateDTO employeeCreateDTO);

    /**
     * Edits an employee.
     *
     * @param editEmployeeDTO The DTO containing information to edit the employee.
     * @return The updated {@link EmployeeDTO} object.
     */
    EmployeeDTO editEmployee(EditEmployeeDTO editEmployeeDTO);

    /**
     * Lists all active employees.
     *
     * @return A list of {@link EmployeeDTO} objects representing all active employees.
     */
    List<EmployeeDTO> getAllActiveEmployees();

    /**
     * Finds an active employee by email.
     *
     * @param email The email address of the worker to find.
     * @return The {@link EmployeeDTO} object representing the found employee.
     */
    EmployeeDTO findActiveEmployeeByEmail(String email);

    /**
     * Finds an active employee by id
     * @param id the id of the employee
     * @return The {@link EmployeeDTO} object representing the found employee
     */
    EmployeeDTO findActiveEmployeeById(Long id);
}
