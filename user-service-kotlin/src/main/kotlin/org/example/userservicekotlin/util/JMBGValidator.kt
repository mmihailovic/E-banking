package org.example.userservicekotlin.util

import org.example.userservicekotlin.exception.JMBGDateMismatchException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class JMBGValidator {
    companion object {
        fun validateJMBG(dateOfBirthTimestamp: Long, jmbg: String) {
            val dateOfBirth =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dateOfBirthTimestamp), ZoneOffset.systemDefault())
            if (jmbg.length == 12) {
                if (dateOfBirth.dayOfMonth != jmbg.substring(0, 1)
                        .toInt() || dateOfBirth.monthValue != jmbg.substring(1, 3)
                        .toInt() || dateOfBirth.year % 1000 != jmbg.substring(3, 6).toInt()
                ) {
                    throw JMBGDateMismatchException()
                }
            } else {
                if (dateOfBirth.dayOfMonth != jmbg.substring(0, 2)
                        .toInt() || dateOfBirth.monthValue != jmbg.substring(2, 4)
                        .toInt() || dateOfBirth.year % 1000 != jmbg.substring(4, 7).toInt()
                ) {
                    throw JMBGDateMismatchException()
                }
            }
        }
    }
}