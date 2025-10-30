package rs.edu.raf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.EditEmployeeDTO;
import rs.edu.raf.dto.EmployeeCreateDTO;
import rs.edu.raf.dto.EmployeeDTO;
import rs.edu.raf.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
@SecurityRequirement(name="jwt")
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping
    @Operation(description = "Create new employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
        return new ResponseEntity<>(employeeService.createNewEmployee(employeeCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(description = "Edit employee")
    public ResponseEntity<EmployeeDTO> editEmployee(@RequestBody @Valid EditEmployeeDTO editEmployeeDTO) {
        return new ResponseEntity<>(employeeService.editEmployee(editEmployeeDTO),HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "All active employees")
    public ResponseEntity<List<EmployeeDTO>> getAllActiveEmployees() {
        return new ResponseEntity<>(employeeService.getAllActiveEmployees(),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @Operation(description = "Get employee by email")
    public ResponseEntity<EmployeeDTO> findEmployeeByEmail(@PathVariable("email") @Parameter(description = "Employee email") String email) {
        return new ResponseEntity<>(employeeService.findActiveEmployeeByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Operation(description = "Get employee by ID")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") @Parameter(description = "Employee id") Long id) {
        return new ResponseEntity<>(employeeService.findActiveEmployeeById(id),HttpStatus.OK);
    }

}

