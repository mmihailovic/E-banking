package org.example.userservicekotlin.model.user

import jakarta.persistence.Entity

@Entity
class Client(
    id: Long? = null,
    phoneNumber: String,
    address: String,
    firstName: String,
    lastName: String,
    JMBG: String,
    dateOfBirth: Long,
    gender: String,
    email: String,
    userPassword: String?,
    active: Boolean,
    roles: MutableList<Role>,
    var accountNumbers: String?
) : User(id, phoneNumber, address, firstName, lastName, JMBG, dateOfBirth, gender, email, userPassword, active, roles) {
}