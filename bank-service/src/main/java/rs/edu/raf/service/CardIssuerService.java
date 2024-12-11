package rs.edu.raf.service;

import rs.edu.raf.dto.CardIssuerCreateDTO;
import rs.edu.raf.dto.CardIssuerDTO;

import java.util.List;

public interface CardIssuerService {
    /**
     * Creates card issuer
     * @param cardIssuerCreateDTO DTO which contains information about card issuer
     * @return {@link CardIssuerDTO} object representing created card issuer
     */
    CardIssuerDTO createCardIssuer(CardIssuerCreateDTO cardIssuerCreateDTO);

    /**
     * Gets all card issuers
     * @return List of {@link CardIssuerDTO} representing card issuers
     */
    List<CardIssuerDTO> getAllCardIssuers();

    /**
     * Deletes card issuer
     * @param id the id of the cart issuer to be deleted
     */
    void deleteCardIssuer(Long id);
}
