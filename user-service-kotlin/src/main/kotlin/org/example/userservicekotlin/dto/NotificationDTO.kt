package org.example.userservicekotlin.dto

import java.io.Serializable

data class NotificationDTO(val email: String, val code: String, val lastName: String, val notificationType: String) :
    Serializable
