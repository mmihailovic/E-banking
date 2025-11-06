package org.example.userservicekotlin.model.user

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Employee(
    id: Long?,
    phoneNumber: String,
    address: String,
    firstName: String,
    lastName: String,
    JMBG: String,
    dateOfBirth: Long,
    gender: String,
    email: String,
    userPassword: String,
    active: Boolean,
    roles: MutableList<Role>,
    var position: String,
    var department: String,
    @ManyToOne val company: Company
) : User(id, phoneNumber, address, firstName, lastName, JMBG, dateOfBirth, gender, email, userPassword, active, roles)
