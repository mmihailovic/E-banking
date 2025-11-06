package org.example.bankservicekotlin.security

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtFilter(val jwtUtil: JwtUtil):OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var jwt = jwtUtil.extractToken()

        if (jwt != null) {
            try {
                if (jwt.startsWith("Bearer ")) jwt = jwt.substring(7)

                val claims = Jwts.parser().setSigningKey("MY JWT SECRET").parseClaimsJws(jwt).body
                val email = claims.subject
                val permission = claims.get("permission", String::class.java)

                val authorities: MutableCollection<SimpleGrantedAuthority> = ArrayList()

                for (role in listOf(
                    *permission.substring(1, permission.length - 1).split(",\\s*".toRegex())
                        .dropLastWhile { it.isEmpty() }.toTypedArray()
                )) {
                    authorities.add(SimpleGrantedAuthority(role))
                }

                val authenticationToken =
                    UsernamePasswordAuthenticationToken(email, null, authorities)

                SecurityContextHolder.getContext().authentication = authenticationToken

                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                println(e.message)
                response.setHeader("error", e.message)
                response.status = HttpStatus.FORBIDDEN.value()
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }

}