package org.example.userservicekotlin.service.impl

import org.example.userservicekotlin.dto.*
import org.example.userservicekotlin.exception.InvalidOldPasswordException
import org.example.userservicekotlin.exception.InvalidTokenException
import org.example.userservicekotlin.exception.UserNotFoundException
import org.example.userservicekotlin.mapper.UserMapper
import org.example.userservicekotlin.model.code.CodeType
import org.example.userservicekotlin.model.user.User
import org.example.userservicekotlin.repository.UserRepository
import org.example.userservicekotlin.security.JwtUtil
import org.example.userservicekotlin.service.CodeService
import org.example.userservicekotlin.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository, val userMapper: UserMapper, val codeService: CodeService, val jwtUtil: JwtUtil, val authenticationManager: AuthenticationManager, val bCryptPasswordEncoder: BCryptPasswordEncoder) : UserService {
    override fun loginUser(loginDto: LoginDTO): LoginResponseDTO {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.username,
                loginDto.password
            )
        )
        val token = jwtUtil.generateToken(loginDto.username)
        val user = authentication.principal as UserDetails

        return LoginResponseDTO(userMapper.userToLoggedUserDTO(user as User), token)
    }

    override fun getLoggedUser(): LoggedUserDTO {
        return userMapper.userToLoggedUserDTO(SecurityContextHolder.getContext().authentication.principal as User)
    }

    override fun changeUserPassword(changePasswordDTO: ChangePasswordDTO): Boolean {
        val user: User = jwtUtil.getCurrentUser()
        if (bCryptPasswordEncoder.matches(changePasswordDTO.oldPassword, user.userPassword)) {
            user.userPassword = bCryptPasswordEncoder.encode(changePasswordDTO.newPassword)
            userRepository.save(user)
            return true
        }
        throw InvalidOldPasswordException()
    }

    override fun changeUserPasswordWithCode(changePasswordWithCodeDTO: ChangePasswordWithCodeDTO): Boolean {
        if (codeService.checkCode(
                changePasswordWithCodeDTO.email,
                changePasswordWithCodeDTO.code,
                CodeType.PASSWORD_RESET
            )
        ) {
            val user: User = userRepository.findByEmailAndActiveIsTrue(changePasswordWithCodeDTO.email)
                .orElseThrow { UserNotFoundException("User with email " + changePasswordWithCodeDTO.email + " not found!") }
            user.userPassword = bCryptPasswordEncoder.encode(changePasswordWithCodeDTO.password)
            userRepository.save(user)
            return true
        }
        throw InvalidTokenException("Token " + changePasswordWithCodeDTO.code + " isn't valid!")
    }
}