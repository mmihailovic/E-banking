package org.example.userservicekotlin.controller

import org.example.userservicekotlin.dto.FavouriteRecipientCreateDTO
import org.example.userservicekotlin.dto.FavouriteRecipientDTO
import org.example.userservicekotlin.service.FavouriteRecipientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/favourite-recipients")
class FavouriteRecipientController(val favouriteRecipientService: FavouriteRecipientService) {
    @PostMapping
    fun addFavouriteRecipient(@RequestBody favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): ResponseEntity<FavouriteRecipientDTO> {
        return ResponseEntity<FavouriteRecipientDTO>(
            favouriteRecipientService.addFavouriteClient(favouriteRecipientCreateDTO),
            HttpStatus.OK
        )
    }

    @GetMapping
    fun getAllFavouriteRecipientsForClient(): ResponseEntity<List<FavouriteRecipientDTO>> {
        return ResponseEntity<List<FavouriteRecipientDTO>>(favouriteRecipientService.findFavouriteClientsForClient(), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteFavouriteRecipientEntryWithId(@PathVariable("id") id: Long?): ResponseEntity<*> {
        favouriteRecipientService.deleteFavouriteClient(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping
    fun editFavouriteRecipient(@RequestBody favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): ResponseEntity<FavouriteRecipientDTO> {
        return ResponseEntity<FavouriteRecipientDTO>(
            favouriteRecipientService.editFavouriteClient(favouriteRecipientCreateDTO),
            HttpStatus.OK
        )
    }
}