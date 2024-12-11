package rs.edu.raf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
@AllArgsConstructor
public class JwtUtil {
    private final String SECRET_KEY = "MY JWT SECRET";
    private HttpServletRequest httpServletRequest;

    public  Claims extractAllClaims(String token) {
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

    public Object extractClaim(String claim) {
        return extractAllClaims(httpServletRequest.getHeader("Authorization").substring(7)).get(claim);
    }

    public boolean isClient() {
        return ((String)extractAllClaims(httpServletRequest.getHeader("Authorization").substring(7)).get("permission"))
                .contains("ROLE_CLIENT");
    }

    public Long getOrderCreator() {
        if(isClient())
            return Long.valueOf((Integer)extractClaim("id"));
        return Long.valueOf((Integer)extractClaim("companyId"));
    }
}
