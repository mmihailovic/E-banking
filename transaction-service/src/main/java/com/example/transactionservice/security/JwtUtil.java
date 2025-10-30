package com.example.transactionservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@AllArgsConstructor
public class JwtUtil {
    private static final String SECRET_KEY = "MY JWT SECRET";
    private final HttpServletRequest httpServletRequest;

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }


    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
    public Long getRealIDForLoggedUser() {
        String jwt = extractToken();
        Claims claims = extractAllClaims(jwt);
        return Long.valueOf((Integer) claims.get("id"));
    }


    public Long getIDForLoggedUser() {
        String jwt = extractToken();
        Claims claims = extractAllClaims(jwt);
        String permissions = (String) claims.get("permission");

        if(permissions.contains("ROLE_CLIENT"))
            return Long.valueOf((Integer) claims.get("id"));

        return Long.valueOf((Integer) claims.get("companyId"));
    }

    public String extractToken() {
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AuthToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
