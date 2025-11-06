package org.example.userservicekotlin.dto

data class EmployeeDTO(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val JMBG: String,
    val dateOfBirth: Long,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val position: String,
    val department: String,
    val roles: List<String>,
    val companyId: Long?
)
