package rs.edu.raf.service;

import rs.edu.raf.dto.CardDTO;
import rs.edu.raf.dto.CreateCardDto;

import java.util.List;

/**
 * This interface defines methods for managing cards.
 */
public interface CardService {

    /**
     * Creates a new card.
     *
     * @param createCardDto The DTO containing information to create the card.
     * @return The {@link CardDTO} object representing the created card.
     */
    CardDTO createCard(CreateCardDto createCardDto);

    /**
     * Deletes a card by its card number.
     *
     * @param cardNumber The card number of the card to delete.
     */
    void deleteCard(String cardNumber);

    /**
     * Blocks a card by its card number.
     *
     * @param cardNumber The card number of the card to block.
     */
    void blockCard(String cardNumber);

    /**
     * Unblock a card by its card number;
     * @param cardNumber the number of the card to unblock
     */
    void unblockCard(String cardNumber);

    /**
     * Deactivates a card by its card number.
     *
     * @param cardNumber The card number of the card to deactivate.
     */
    void deactivateCard(String cardNumber);

    /**
     * Retrieves all cards.
     *
     * @return A list of {@link CardDTO} objects representing all cards.
     */
    List<CardDTO> getAllCards();

    /**
     * Retrieves all cards for a specific user.
     *
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of {@link CardDTO} objects representing all cards for the specified user.
     */
    List<CardDTO> getAllCardsForUser(Long userId);
}
