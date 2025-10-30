package rs.edu.raf.service;

import rs.edu.raf.dto.CreditDTO;
import rs.edu.raf.dto.CreditRequestCreateDto;
import rs.edu.raf.dto.CreditRequestDTO;
import rs.edu.raf.model.credit.CreditRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface defines methods for managing credit requests and credits.
 */
public interface CreditService {

    /**
     * Applies for credit.
     *
     * @param creditRequestCreateDto The DTO containing information to apply for credit.
     * @return The {@link CreditRequestDTO} object representing credit request
     */
    CreditRequestDTO applyForCredit(CreditRequestCreateDto creditRequestCreateDto);

    /**
     * Approves a credit request.
     *
     * @param id The ID of the credit request to approve.
     * @return The {@link CreditRequestDTO} object representing credit request with updated status
     */
    CreditRequestDTO approveCreditRequest(Long id);

    /**
     * Denies a credit request.
     *
     * @param id The ID of the credit request to deny.
     * @return The {@link CreditRequestDTO} object representing credit request with updated status
     */
    CreditRequestDTO dennyCreditRequest(Long id);

    /**
     * Retrieves all credit requests with the specified status.
     *
     * @param status The status of the credit requests to retrieve.
     * @return A list of {@link CreditRequestDTO} objects representing the credit requests.
     */
    List<CreditRequestDTO> getAllCreditRequestsWithStatus(String status);

    /**
     * Retrieves all credit requests for a specific user with the specified status.
     *
     * @param userId The ID of the user whose credit requests are to be retrieved.
     * @param status The status of the credit requests to retrieve.
     * @return A list of {@link CreditRequestDTO} objects representing the credit requests.
     */
    List<CreditRequestDTO> getAllCreditRequestForUserWithStatus(Long userId, String status);

    /**
     * Creates a credit from specified credit request.
     *
     * @param creditRequest The credit request.
     */
    void createCredit(CreditRequest creditRequest);

    /**
     * Retrieves all credits
     * @return List of {@link CreditDTO} objects representing the credits
     */
    List<CreditDTO> getAllCredits();

    /**
     * This method processes credit installment
     */
    void processCreditInstallment();

    /**
     * Retrieves detailed information about a credit.
     *
     * @param id The ID of the credit.
     * @return The {@link CreditDTO} object representing information about the credit.
     */
    CreditDTO getDetailedCredit(Long id);

    /**
     * Calculates installment amount for specified credit request based on nominal interest rate,
     * loan term and loan amount
     * @param creditRequest the credit request
     * @return {@link BigDecimal} object representing installment amount
     */
    BigDecimal calculateInstallmentAmountForCredit(CreditRequest creditRequest);
}
