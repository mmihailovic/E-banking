package org.example.bankservicekotlin.model.credit

import jakarta.persistence.*
import org.example.bankservicekotlin.model.accounts.BankAccount
import java.math.BigDecimal

@Entity
class CreditRequest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var clientId: Long?,
    @ManyToOne
    val creditType: CreditType,
    val loanAmount: BigDecimal,
    val loanPurpose: String,
    val salary: BigDecimal,
    val phoneNumber: String,
    val permanentEmployee: Boolean,
    val currentEmploymentPeriod: Int,
    val loanTerm: Long,
    @ManyToOne
    val bankAccount: BankAccount,
    @Enumerated
    var creditRequestStatus: CreditRequestStatus,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "creditRequest")
    val credits: MutableList<Credit>?
) {
}