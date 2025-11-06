package org.example.userservicekotlin.security

import org.example.userservicekotlin.model.user.User
import org.example.userservicekotlin.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(val userRepository: UserRepository):UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Optional<User> = userRepository.findByEmailAndActiveIsTrue(username)

        if (user.isPresent) return user.get()
        throw UsernameNotFoundException(username)
    }
}