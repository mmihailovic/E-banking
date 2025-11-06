package org.example.userservicekotlin.mapper

import org.example.userservicekotlin.dto.EmployeeCreateDTO
import org.example.userservicekotlin.dto.EmployeeDTO
import org.example.userservicekotlin.model.user.Employee
import org.example.userservicekotlin.model.user.Role
import org.example.userservicekotlin.repository.CompanyRepository
import org.example.userservicekotlin.repository.RoleRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class EmployeeMapper(
    val roleRepository: RoleRepository,
    val companyRepository: CompanyRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun employeeCreateDTOtoEmployee(employeeCreateDTO: EmployeeCreateDTO): Employee {
        val roles: MutableList<Role> =
            employeeCreateDTO.roles.stream().map { role -> roleRepository.findByName(role).orElseThrow() }.toList()

        val company = companyRepository.findById(employeeCreateDTO.companyId).orElseThrow()

        return Employee(
            null,
            employeeCreateDTO.phoneNumber,
            employeeCreateDTO.address,
            employeeCreateDTO.firstName,
            employeeCreateDTO.lastName,
            employeeCreateDTO.JMBG,
            employeeCreateDTO.dateOfBirth,
            employeeCreateDTO.gender,
            employeeCreateDTO.email,
            bCryptPasswordEncoder.encode(employeeCreateDTO.password),
            employeeCreateDTO.active,
            roles,
            employeeCreateDTO.position,
            employeeCreateDTO.department,
            company
        )
    }

    fun employeeToEmployeeDTO(employee: Employee): EmployeeDTO {
        return EmployeeDTO(
            employee.id,
            employee.firstName,
            employee.lastName,
            employee.JMBG,
            employee.dateOfBirth,
            employee.gender,
            employee.email,
            employee.phoneNumber,
            employee.address,
            employee.position,
            employee.department,
            employee.roles.map { role -> role.name }.toList(),
            employee.company.id
        )
    }
}