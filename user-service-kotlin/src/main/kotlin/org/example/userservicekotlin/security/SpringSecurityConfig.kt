package org.example.userservicekotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SpringSecurityConfig(val jwtFilter: JwtFilter) : WebSecurityCustomizer {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .cors { cors: CorsConfigurer<HttpSecurity?> -> cors.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(
                        "/swagger-ui/**", "/api-docs/**", "/swagger-resources/**",
                        "/user/login", "/user/logout", "/user/generate/**", "/clients/register", "/user/reset-password",
                        "/user/*/code/*"
                    ).permitAll()
                    .requestMatchers("/clients/*/account/*", "/company/*/account/*")
                    .hasAuthority("ROLE_MANAGE_BANK_ACCOUNTS")
                    .requestMatchers(
                        "/clients/**", "/clients", "/clients/add", "/clients/email/**",
                        "/clients/jmbg/**", "/clients/phone-number/**", "/clients/id/**"
                    ).hasAuthority("ROLE_MANAGE_CLIENTS")
                    .requestMatchers("/company/**", "/company").hasAuthority("ROLE_MANAGE_COMPANIES")
                    .requestMatchers("/employee", "/employee/**").hasAuthority("ROLE_MANAGE_EMPLOYEES")
                    .anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:5002")

        configuration.allowedMethods = mutableListOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders =
            mutableListOf("Authorization", "Cache-Control", "Content-Type")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun customize(web: WebSecurity?) {

    }
}