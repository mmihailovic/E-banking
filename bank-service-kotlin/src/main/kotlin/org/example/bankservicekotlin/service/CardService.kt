package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.CardDTO
import org.example.bankservicekotlin.dto.CreateCardDTO

interface CardService {
    /**
     * Creates a new card.
     *
     * @param createCardDto The DTO containing information to create the card.
     * @return The [CardDTO] object representing the created card.
     */
    fun createCard(createCardDto: CreateCardDTO): CardDTO

    /**
     * Deletes a card by its card number.
     *
     * @param cardNumber The card number of the card to delete.
     */
    fun deleteCard(cardNumber: String)

    /**
     * Blocks a card by its card number.
     *
     * @param cardNumber The card number of the card to block.
     */
    fun blockCard(cardNumber: String)

    /**
     * Unblock a card by its card number;
     * @param cardNumber the number of the card to unblock
     */
    fun unblockCard(cardNumber: String)

    /**
     * Deactivates a card by its card number.
     *
     * @param cardNumber The card number of the card to deactivate.
     */
    fun deactivateCard(cardNumber: String)

    /**
     * Retrieves all cards.
     *
     * @return A list of [CardDTO] objects representing all cards.
     */
    fun getAllCards(): List<CardDTO>

    /**
     * Retrieves all cards for a specific user.
     *
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of [CardDTO] objects representing all cards for the specified user.
     */
    fun getAllCardsForUser(userId: Long): List<CardDTO>
}