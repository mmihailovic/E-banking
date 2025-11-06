package org.example.bankservicekotlin.model

import jakarta.persistence.*

@Entity
class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true)
    val name: String
) {
}