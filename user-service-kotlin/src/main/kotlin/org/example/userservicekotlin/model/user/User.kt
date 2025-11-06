package org.example.userservicekotlin.model.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class User(
    id: Long?,
    phoneNumber: String,
    address: String,
    val firstName: String,
    var lastName: String,
    @Column(unique = true)
    val JMBG: String,
    val dateOfBirth: Long,
    var gender: String,

    @Column(unique = true)
    var email: String,
    @Column(name = "password")
    var userPassword: String?,
    var active: Boolean,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: MutableList<Role>
) : BankAccountHolder(id, phoneNumber, address), UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return roles.map { role: Role -> SimpleGrantedAuthority(role.name) }
            .toList()
    }

    override fun getPassword(): String {
        return userPassword!!
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return active
    }
}
