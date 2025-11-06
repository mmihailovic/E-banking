package org.example.stockmarketservicekotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    private val httpServletRequest: HttpServletRequest
) {
    private val SECRET_KEY: String = "MY JWT SECRET"

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }


    fun validateToken(token: String, user: UserDetails): Boolean {
        return (user.username == extractUsername(token) && !isTokenExpired(token))
    }

    fun getRealIDForLoggedUser(): Long? {
        val jwt = extractToken() ?: return null
        val claims = extractAllClaims(jwt)
        return claims["id"] as Long
    }


    fun getIDForLoggedUser(): Long? {
        val jwt = extractToken() ?: return null
        val claims = extractAllClaims(jwt)
        val permissions = claims["permission"] as String

        if (permissions.contains("ROLE_CLIENT")) return claims["id"] as Long

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