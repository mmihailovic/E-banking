package org.example.bankservicekotlin.model.credit

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Credit(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @ManyToOne
    val creditRequest: CreditRequest,
    val contractDate: Long,
    val loanMaturityDate: Long,
    var installmentAmount: BigDecimal,
    var remainingDebt: BigDecimal,
    var nextInstallmentDate: Long
) {
}