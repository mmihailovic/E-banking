package org.example.userservicekotlin.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.example.userservicekotlin.dto.*
import org.example.userservicekotlin.model.code.CodeType
import org.example.userservicekotlin.service.CodeService
import org.example.userservicekotlin.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService, val codeService: CodeService) {
    @PutMapping("/change-password")
    fun changePassword(@RequestBody changePasswordDTO: ChangePasswordDTO): ResponseEntity<*> {
        return ResponseEntity<Any?>(userService.changeUserPassword(changePasswordDTO), HttpStatus.OK)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody changePasswordWithCodeDTO: @Valid ChangePasswordWithCodeDTO): ResponseEntity<*> {
        return ResponseEntity<Any?>(userService.changeUserPasswordWithCode(changePasswordWithCodeDTO), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: @Valid LoginDTO, response: HttpServletResponse): ResponseEntity<*> {
        val loginResponseDTO: LoginResponseDTO = userService.loginUser(loginRequest)

        val cookie = Cookie("AuthToken", loginResponseDTO.token)
        cookie.isHttpOnly = true
        cookie.secure = false
        cookie.path = "/"
        cookie.maxAge = 60 * 60
        response.addCookie(cookie)

        return ResponseEntity<Any?>(loginResponseDTO.loggedUser, HttpStatus.OK)
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<*> {
        val cookie = Cookie("AuthToken", null)
        cookie.maxAge = 0
        cookie.isHttpOnly = true
        cookie.secure = false
        cookie.path = "/"
        response.addCookie(cookie)

        return ResponseEntity.ok().build<Any>()
    }

    @PostMapping("/me")
    fun me(): ResponseEntity<*> {
        return ResponseEntity<Any?>(userService.getLoggedUser(), HttpStatus.OK)
    }

    @PostMapping("/generate/{type}")
    fun generateCode(
        @RequestBody generateCodeDTO: @Valid GenerateCodeDTO,
        @PathVariable("type") type: String
    ): ResponseEntity<*> {
        codeService.addCode(generateCodeDTO.email, type)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/{email}/code/{code}")
    fun checkPaymentCode(
        @PathVariable("code") code: String,
        @PathVariable("email") email: String
    ): ResponseEntity<Boolean> {
        return ResponseEntity<Boolean>(codeService.checkCode(email, code, CodeType.PAYMENT_CODE), HttpStatus.OK)
    }
}