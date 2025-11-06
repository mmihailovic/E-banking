package org.example.bankservicekotlin.model

import jakarta.persistence.*

@Entity
class Currency(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(unique = true)
    val name: String,
    val code: String,
    val symbol: String,
    @ManyToOne
    val country: Country
) {
}