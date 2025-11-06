package org.example.bankservicekotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(val httpServletRequest: HttpServletRequest) {
    private val SECRET_KEY: String = "MY JWT SECRET KEY"

    fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()
    }

    fun extractUsername(token: String?): String {
        return extractAllClaims(token).getSubject()
    }

    fun isTokenExpired(token: String?): Boolean {
        return extractAllClaims(token).getExpiration().before(Date())
    }


    fun validateToken(token: String?, user: UserDetails): Boolean {
        return (user.username == extractUsername(token) && !isTokenExpired(token))
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