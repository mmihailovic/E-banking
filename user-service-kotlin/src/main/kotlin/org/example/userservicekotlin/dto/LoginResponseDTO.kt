package org.example.userservicekotlin.dto

data class LoginResponseDTO(val loggedUser: LoggedUserDTO, val token: String)
