package org.example.userservicekotlin.service.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.userservicekotlin.model.code.Code
import org.example.userservicekotlin.model.code.CodeType
import org.example.userservicekotlin.model.user.User
import org.example.userservicekotlin.repository.CodeRepository
import org.example.userservicekotlin.repository.UserRepository
import org.example.userservicekotlin.service.CodeService
import org.example.userservicekotlin.exception.UserNotFoundException
import org.example.userservicekotlin.exception.InvalidTokenException
import org.example.userservicekotlin.dto.NotificationDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class CodeServiceImpl(
    val codeRepository: CodeRepository, val userRepository: UserRepository, val jmsTemplate: JmsTemplate,
    @Value("\${notifications.destination}")
    private val destination: String, val objectMapper: ObjectMapper
) : CodeService {
    override fun addCode(email: String, codeType: String): String {
        try {
            val user: User = userRepository.findByEmailAndActiveIsTrue(email)
                .orElseThrow { UserNotFoundException("User with email $email not found!") }

            val type: CodeType = CodeType.valueOf(codeType)

            val existingCode: Optional<Code> = codeRepository.findByUserEmailAndCodeType(email, type)

            existingCode.ifPresent(codeRepository::delete)

            val code = UUID.randomUUID().toString()
            val expirationDate = System.currentTimeMillis() + 1000 * 60 * 15

            codeRepository.save(Code(null, user, code, expirationDate, type))
            jmsTemplate.convertAndSend(
                destination,
                objectMapper.writeValueAsString(NotificationDTO(email, code, user.lastName, type.toString()))
            )
            return code
        } catch (exception: JsonProcessingException) {
            exception.printStackTrace()
            throw RuntimeException()
        }
    }

    override fun checkCode(email: String, code: String, codeType: CodeType): Boolean {
        val codeObject: Code = codeRepository.findByUserEmailAndCodeType(email, codeType)
            .orElseThrow { InvalidTokenException("Token $code doesn't exist!") }

        if (codeObject.code == code && codeObject.expirationDate >= System.currentTimeMillis()) {
            codeRepository.delete(codeObject)
            return true
        }

        return false
    }

}