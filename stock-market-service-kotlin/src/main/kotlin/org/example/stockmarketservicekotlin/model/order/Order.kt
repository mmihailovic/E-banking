package org.example.stockmarketservicekotlin.model.order

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Enumerated(EnumType.STRING)
    val orderAction: OrderAction,
    val ticker: String,
    var quantity: Int,
    val orderCreator: Long
) {
}