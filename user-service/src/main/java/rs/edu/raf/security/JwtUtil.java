package rs.edu.raf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.model.user.Client;
import rs.edu.raf.model.user.Employee;
import rs.edu.raf.model.user.User;
import rs.edu.raf.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "MY JWT SECRET";
    @Autowired
    private UserRepository userRepository;
    @Autowired
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

    public String generateToken(String username){
        User user = userRepository.findByEmailAndActiveIsTrue(username)
                .orElseThrow(()->new UserNotFoundException("User with username " + username + " not found!"));

        Map<String, Object> claims = new HashMap<>();

        claims.put("permission",user.getRoles().toString());
        claims.put("id",user.getId());

        if(user instanceof Employee employee) {
            claims.put("companyId", employee.getCompany().getId());
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getTokenDuration(user)))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return userRepository.findByEmailAndActiveIsTrue(((UserDetails) authentication.getPrincipal()).getUsername()).orElseThrow();
        }
        throw new RuntimeException();
    }

    private int getTokenDuration(User user) {
        if(user instanceof Client) {
            return 1000 * 60 * 60; // 1 hour
        }
        if(user instanceof Employee) {
            return 1000 * 60 * 60 * 8; // 8 hours
        }

        return 0;
    }

    public Long getRealIDForLoggedUser() {
        String jwt = extractToken();
        Claims claims = extractAllClaims(jwt);
        return (Long) claims.get("id");
    }


    public Long getIDForLoggedUser() {
        String jwt = extractToken();
        Claims claims = extractAllClaims(jwt);
        String permissions = (String) claims.get("permission");

        if(permissions.contains("ROLE_CLIENT"))
            return (Long) claims.get("id");

        return (Long) claims.get("companyId");
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
