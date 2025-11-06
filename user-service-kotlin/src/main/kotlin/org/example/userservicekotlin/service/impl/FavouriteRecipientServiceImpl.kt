package org.example.userservicekotlin.service.impl

import org.example.userservicekotlin.dto.FavouriteRecipientCreateDTO
import org.example.userservicekotlin.dto.FavouriteRecipientDTO
import org.example.userservicekotlin.mapper.FavouriteRecipientMapper
import org.example.userservicekotlin.model.user.Client
import org.example.userservicekotlin.repository.FavouriteRecipientRepository
import org.example.userservicekotlin.security.JwtUtil
import org.example.userservicekotlin.service.FavouriteRecipientService
import org.springframework.stereotype.Service

@Service
class FavouriteRecipientServiceImpl(
    val favouriteRecipientRepository: FavouriteRecipientRepository,
    val favouriteRecipientMapper: FavouriteRecipientMapper,
    val jwtUtil: JwtUtil
) : FavouriteRecipientService {

    override fun addFavouriteClient(favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): FavouriteRecipientDTO {
        return favouriteRecipientMapper.favouriteRecipientToFavouriteRecipientDTO(
            favouriteRecipientRepository.save(
                favouriteRecipientMapper.favouriteRecipientRequestDTOtoFavouriteRecipient(
                    favouriteRecipientCreateDTO,
                    jwtUtil.getCurrentUser() as Client
                )
            )
        )
    }

    override fun editFavouriteClient(favouriteRecipientCreateDTO: FavouriteRecipientCreateDTO): FavouriteRecipientDTO {
        return favouriteRecipientMapper.favouriteRecipientToFavouriteRecipientDTO(
            favouriteRecipientRepository.save(
                favouriteRecipientMapper.favouriteRecipientRequestDTOtoFavouriteRecipient(
                    favouriteRecipientCreateDTO,
                    jwtUtil.getCurrentUser() as Client
                )
            )
        )
    }

    override fun deleteFavouriteClient(id: Long?) {
        favouriteRecipientRepository.deleteById(id!!)
    }

    override fun findFavouriteClientsForClient(): List<FavouriteRecipientDTO> {
        return favouriteRecipientRepository.findByClient_Id(jwtUtil.getCurrentUser().id!!)
            .map { favouriteRecipient ->
                favouriteRecipientMapper.favouriteRecipientToFavouriteRecipientDTO(
                    favouriteRecipient
                )
            }.toList()
    }
}