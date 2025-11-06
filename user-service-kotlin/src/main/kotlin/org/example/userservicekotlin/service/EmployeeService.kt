package org.example.userservicekotlin.service

import org.example.userservicekotlin.dto.EditEmployeeDTO
import org.example.userservicekotlin.dto.EmployeeCreateDTO
import org.example.userservicekotlin.dto.EmployeeDTO

interface EmployeeService {
    /**
     * Creates a new employee.
     *
     * @param employeeCreateDTO The DTO containing information about the new employee.
     * @return The created [EmployeeDTO] object.
     */
    fun createNewEmployee(employeeCreateDTO: EmployeeCreateDTO): EmployeeDTO

    /**
     * Edits an employee.
     *
     * @param editEmployeeDTO The DTO containing information to edit the employee.
     * @return The updated [EmployeeDTO] object.
     */
    fun editEmployee(editEmployeeDTO: EditEmployeeDTO): EmployeeDTO

    /**
     * Lists all active employees.
     *
     * @return A list of [EmployeeDTO] objects representing all active employees.
     */
    fun getAllActiveEmployees(): List<EmployeeDTO>

    /**
     * Finds an active employee by email.
     *
     * @param email The email address of the worker to find.
     * @return The [EmployeeDTO] object representing the found employee.
     */
    fun findActiveEmployeeByEmail(email: String): EmployeeDTO

    /**
     * Finds an active employee by id
     * @param id the id of the employee
     * @return The [EmployeeDTO] object representing the found employee
     */
    fun findActiveEmployeeById(id: Long): EmployeeDTO
}