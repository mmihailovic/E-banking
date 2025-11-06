package org.example.userservicekotlin.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(val customUserDetailsService: CustomUserDetailsService, val jwtUtil: JwtUtil): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.getServletPath() == "/api/user/login") {
            filterChain.doFilter(request, response)
        } else {
            var jwt = jwtUtil.extractToken()
            val username: String?

            if (jwt != null) {
                if (jwt.startsWith("Bearer ")) jwt = jwt.substring(7)
                username = jwtUtil.extractUsername(jwt)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: UserDetails = this.customUserDetailsService.loadUserByUsername(username)

                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )
                        usernamePasswordAuthenticationToken.details =
                            WebAuthenticationDetailsSource().buildDetails(request)

                        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                    }
                }
                filterChain.doFilter(request, response)
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}