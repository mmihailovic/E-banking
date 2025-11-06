package org.example.userservicekotlin.mapper

import org.example.userservicekotlin.dto.LoggedUserDTO
import org.example.userservicekotlin.model.user.Employee
import org.example.userservicekotlin.model.user.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun userToLoggedUserDTO(user: User): LoggedUserDTO {
        if (user is Employee) {
            return LoggedUserDTO(
                user.id,
                user.username,
                user.company.id,
                user.roles.map{role->role.name}.toList()
            )
        }
        return LoggedUserDTO(
            user.id,
            user.username,
            null,
            user.roles.map {role ->role.name}.toList()
        )
    }
}