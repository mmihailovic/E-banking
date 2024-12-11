package rs.edu.raf.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig implements WebSecurityCustomizer {

    private final JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-resources/**",
                                "/user/login", "/user/generate/**", "/clients/register", "/user/reset-password",
                                "/user/*/code/*").permitAll()
                        .requestMatchers("/clients/*/account/*", "/company/*/account/*").hasAuthority("ROLE_MANAGE_BANK_ACCOUNTS")
                        .requestMatchers("/clients/**", "/clients", "/clients/add", "/clients/email/**",
                                "/clients/jmbg/**", "/clients/phone-number/**", "/clients/id/**").hasAuthority("ROLE_MANAGE_CLIENTS")
                        .requestMatchers("/company/**", "/company").hasAuthority("ROLE_MANAGE_COMPANIES")
                        .requestMatchers("/employee","/employee/**").hasAuthority("ROLE_MANAGE_EMPLOYEES")
                        .anyRequest().authenticated()
                )
                .csrf()
                .disable()
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

}
