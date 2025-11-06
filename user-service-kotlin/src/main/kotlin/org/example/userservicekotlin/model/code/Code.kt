package org.example.userservicekotlin.model.code

import jakarta.persistence.*
import org.example.userservicekotlin.model.user.User

@Entity
class Code(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne
    val user: User,
    @Column(unique = true)
    val code: String,
    val expirationDate: Long,
    @Enumerated(value = EnumType.STRING)
    val codeType: CodeType
) {
}