package org.example.userservicekotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.example.userservicekotlin.model.user.Employee
import org.example.userservicekotlin.model.user.User
import org.example.userservicekotlin.repository.UserRepository
import org.example.userservicekotlin.exception.UserNotFoundException
import org.example.userservicekotlin.model.user.Client
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(val userRepository: UserRepository, val httpServletRequest: HttpServletRequest) {
    private val SECRET_KEY: String = "MY JWT SECRET KEY"

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    fun generateToken(username: String): String {
        val user: User = userRepository.findByEmailAndActiveIsTrue(username)
            .orElseThrow { UserNotFoundException("User with username $username not found!") }

        val claims: MutableMap<String, Any> = HashMap()

        claims["permission"] = user.roles.toString()
        claims["id"] = user.id.toString()

        if (user is Employee) {
            claims["companyId"] = user.company.id.toString()
        }

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + getTokenDuration(user)))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact()
    }

    fun validateToken(token: String, user: UserDetails): Boolean {
        return (user.username == extractUsername(token) && !isTokenExpired(token))
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.principal is UserDetails) {
            return userRepository.findByEmailAndActiveIsTrue((authentication.principal as UserDetails).username)
                .orElseThrow()
        }
        throw RuntimeException()
    }

    private fun getTokenDuration(user: User): Int {
        if (user is Client) {
            return 1000 * 60 * 60 // 1 hour
        }
        if (user is Employee) {
            return 1000 * 60 * 60 * 8 // 8 hours
        }

        return 0
    }

    fun getRealIDForLoggedUser(): Long {
        val jwt = extractToken()
        val claims: Claims = extractAllClaims(jwt!!)
        return claims["id"] as Long
    }


    fun getIDForLoggedUser(): Long {
        val jwt = extractToken()
        val claims: Claims = extractAllClaims(jwt!!)

        if ((claims["permission"] as List<*>).contains("ROLE_CLIENT")) return claims["id"] as Long

        return claims["companyId"] as Long
    }

    fun extractToken(): String? {
        val cookies = httpServletRequest.cookies

        if (cookies != null) {
            for (cookie in cookies) {
                if ("AuthToken" == cookie.name) {
                    return cookie.value
                }
            }
        }

        return null
    }
}