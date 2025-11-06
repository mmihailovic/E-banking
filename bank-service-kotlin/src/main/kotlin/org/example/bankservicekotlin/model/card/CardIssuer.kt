package org.example.bankservicekotlin.model.card

import jakarta.persistence.*

@Entity
class CardIssuer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(unique = true)
    val name: String,
    val MII: Int,
    val BIN: Int,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "cardIssuer")
    val cards: MutableList<Card>?
) {
}