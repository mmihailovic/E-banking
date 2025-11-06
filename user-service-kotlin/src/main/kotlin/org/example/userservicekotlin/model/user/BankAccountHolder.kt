package org.example.userservicekotlin.model.user

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class BankAccountHolder (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(unique = true) var phoneNumber: String,
    var address: String
) {
}