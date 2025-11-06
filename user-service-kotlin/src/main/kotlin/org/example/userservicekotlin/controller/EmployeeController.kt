package org.example.userservicekotlin.controller

import jakarta.validation.Valid
import org.example.userservicekotlin.dto.EditEmployeeDTO
import org.example.userservicekotlin.dto.EmployeeCreateDTO
import org.example.userservicekotlin.dto.EmployeeDTO
import org.example.userservicekotlin.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employee")
class EmployeeController(val employeeService: EmployeeService) {
    @PostMapping
    fun createEmployee(@RequestBody employeeCreateDTO: @Valid EmployeeCreateDTO): ResponseEntity<EmployeeDTO> {
        return ResponseEntity<EmployeeDTO>(employeeService.createNewEmployee(employeeCreateDTO), HttpStatus.CREATED)
    }

    @PutMapping
    fun editEmployee(@RequestBody editEmployeeDTO: @Valid EditEmployeeDTO): ResponseEntity<EmployeeDTO> {
        return ResponseEntity<EmployeeDTO>(employeeService.editEmployee(editEmployeeDTO), HttpStatus.OK)
    }

    @GetMapping
    fun getAllActiveEmployees(): ResponseEntity<List<EmployeeDTO>> {
        return ResponseEntity<List<EmployeeDTO>>(employeeService.getAllActiveEmployees(), HttpStatus.OK)
    }

    @GetMapping("/email/{email}")
    fun findEmployeeByEmail(@PathVariable("email") email: String): ResponseEntity<EmployeeDTO> {
        return ResponseEntity<EmployeeDTO>(employeeService.findActiveEmployeeByEmail(email), HttpStatus.OK)
    }

    @GetMapping("/id/{id}")
    fun findEmployeeById(@PathVariable("id") id: Long): ResponseEntity<EmployeeDTO> {
        return ResponseEntity<EmployeeDTO>(employeeService.findActiveEmployeeById(id), HttpStatus.OK)
    }

}