package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.CreditDTO
import org.example.bankservicekotlin.dto.CreditRequestCreateDTO
import org.example.bankservicekotlin.dto.CreditRequestDTO
import org.example.bankservicekotlin.model.credit.CreditRequest
import java.math.BigDecimal

interface CreditService {
    /**
     * Applies for credit.
     *
     * @param creditRequestCreateDto The DTO containing information to apply for credit.
     * @return The [CreditRequestDTO] object representing credit request
     */
    fun applyForCredit(creditRequestCreateDto: CreditRequestCreateDTO): CreditRequestDTO

    /**
     * Approves a credit request.
     *
     * @param id The ID of the credit request to approve.
     * @return The [CreditRequestDTO] object representing credit request with updated status
     */
    fun approveCreditRequest(id: Long): CreditRequestDTO

    /**
     * Denies a credit request.
     *
     * @param id The ID of the credit request to deny.
     * @return The [CreditRequestDTO] object representing credit request with updated status
     */
    fun dennyCreditRequest(id: Long): CreditRequestDTO

    /**
     * Retrieves all credit requests with the specified status.
     *
     * @param status The status of the credit requests to retrieve.
     * @return A list of [CreditRequestDTO] objects representing the credit requests.
     */
    fun getAllCreditRequestsWithStatus(status: String): List<CreditRequestDTO>

    /**
     * Retrieves all credit requests for a specific user with the specified status.
     *
     * @param userId The ID of the user whose credit requests are to be retrieved.
     * @param status The status of the credit requests to retrieve.
     * @return A list of [CreditRequestDTO] objects representing the credit requests.
     */
    fun getAllCreditRequestForUserWithStatus(userId: Long, status: String): List<CreditRequestDTO>

    /**
     * Creates a credit from specified credit request.
     *
     * @param creditRequest The credit request.
     */
    fun createCredit(creditRequest: CreditRequest)

    /**
     * Retrieves all credits
     * @return List of [CreditDTO] objects representing the credits
     */
    fun getAllCredits(): List<CreditDTO>

    /**
     * This method processes credit installment
     */
    fun processCreditInstallment()

    /**
     * Retrieves detailed information about a credit.
     *
     * @param id The ID of the credit.
     * @return The [CreditDTO] object representing information about the credit.
     */
    fun getDetailedCredit(id: Long): CreditDTO

    /**
     * Calculates installment amount for specified credit request based on nominal interest rate,
     * loan term and loan amount
     * @param creditRequest the credit request
     * @return [BigDecimal] object representing installment amount
     */
    fun calculateInstallmentAmountForCredit(creditRequest: CreditRequest): BigDecimal
}