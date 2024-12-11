package rs.edu.raf.service;

import rs.edu.raf.dto.FavouriteRecipientDTO;
import rs.edu.raf.dto.FavouriteRecipientRequestDTO;

import java.util.List;

/**
 * This interface defines methods for managing favorite users.
 */
public interface FavouriteRecipientService {

    /**
     * Adds a favorite user.
     *
     * @param favouriteRecipientRequestDTO The DTO containing information about the favorite user to add.
     * @return The added {@link FavouriteRecipientDTO} object.
     */
    FavouriteRecipientDTO addFavouriteClient(FavouriteRecipientRequestDTO favouriteRecipientRequestDTO);

    /**
     * Edits a favorite user.
     *
     * @param favouriteRecipientRequestDTO The DTO containing updated information about the favorite user.
     * @return The edited {@link FavouriteRecipientDTO} object.
     */
    FavouriteRecipientDTO editFavouriteClient(FavouriteRecipientRequestDTO favouriteRecipientRequestDTO);

    /**
     * Deletes a favorite user by ID.
     *
     * @param id The ID of the favorite user to delete.
     */
    void deleteFavouriteClient(Long id);

    /**
     * Finds favorite users for currently logged user.
     *
     * @return A list of {@link FavouriteRecipientDTO} objects representing the favorite users of the specified user.
     */
    List<FavouriteRecipientDTO> findFavouriteClientsForClient();

}
