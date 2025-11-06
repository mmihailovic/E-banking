package org.example.bankservicekotlin.model.credit

import jakarta.persistence.*
import org.example.bankservicekotlin.model.Currency
import java.math.BigDecimal

@Entity
class CreditType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    val name: String,
    val nominalInterestRate: BigDecimal,
    val minLoanTerm: Int,
    val maxLoanTerm: Int,
    val maxLoanAmount: BigDecimal,
    val prepayment: BigDecimal,
    @ManyToOne
    val currency: Currency
) {
}