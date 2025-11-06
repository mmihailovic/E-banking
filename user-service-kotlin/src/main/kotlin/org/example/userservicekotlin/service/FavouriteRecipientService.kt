package org.example.userservicekotlin.service

import org.example.userservicekotlin.dto.FavouriteRecipientCreateDTO
import org.example.userservicekotlin.dto.FavouriteRecipientDTO

interface FavouriteRecipientService {
    /**
     * Adds a favorite user.
     *
     * @param favouriteRecipientCreateDTO The DTO containing information about the favorite user to add.
     * @return The added [FavouriteRecipientDTO] object.
     */
    fun addFavouriteClient(favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): FavouriteRecipientDTO

    /**
     * Edits a favorite user.
     *
     * @param favouriteRecipientCreateDTO The DTO containing updated information about the favorite user.
     * @return The edited [FavouriteRecipientDTO] object.
     */
    fun editFavouriteClient(favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): FavouriteRecipientDTO?

    /**
     * Deletes a favorite user by ID.
     *
     * @param id The ID of the favorite user to delete.
     */
    fun deleteFavouriteClient(id: Long?)

    /**
     * Finds favorite users for currently logged user.
     *
     * @return A list of [FavouriteRecipientDTO] objects representing the favorite users of the specified user.
     */
    fun findFavouriteClientsForClient(): List<FavouriteRecipientDTO>
}