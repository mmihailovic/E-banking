package rs.edu.raf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final String jwtSecretKey = "MY JWT SECRET";

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtUtil.extractToken();

        if(jwt != null) {
            try {
                if(jwt.startsWith("Bearer ")) jwt = jwt.substring(7);
                Claims claims = Jwts.parser().setSigningKey("MY JWT SECRET").parseClaimsJws(jwt).getBody();
                String email = claims.getSubject();
                String permission = claims.get("permission", String.class);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                for(String role: Arrays.asList(permission.substring(1, permission.length() - 1).split(",\\s*"))) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
