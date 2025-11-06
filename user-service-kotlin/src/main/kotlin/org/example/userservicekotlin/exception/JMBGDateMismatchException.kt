package org.example.userservicekotlin.exception

import org.springframework.http.HttpStatus

data class JMBGDateMismatchException(override val message: String = "The date of birth and JMBG do not match!") :
    CustomException(message, HttpStatus.BAD_REQUEST)
