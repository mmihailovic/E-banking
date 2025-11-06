package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.dto.CardIssuerCreateDTO
import org.example.bankservicekotlin.dto.CardIssuerDTO
import org.example.bankservicekotlin.exception.CardIssuerNotFoundException
import org.example.bankservicekotlin.mapper.CardIssuerMapper
import org.example.bankservicekotlin.model.card.CardIssuer
import org.example.bankservicekotlin.repository.CardIssuerRepository
import org.example.bankservicekotlin.service.CardIssuerService
import org.springframework.stereotype.Service

@Service
class CardIssuerServiceImpl(
    private val cardIssuerRepository: CardIssuerRepository,
    private val cardIssuerMapper: CardIssuerMapper
): CardIssuerService {
    override fun createCardIssuer(cardIssuerCreateDTO: CardIssuerCreateDTO): CardIssuerDTO {
        return cardIssuerMapper.cardIssuerToCardIssuerDTO(
            cardIssuerRepository.save(
                cardIssuerMapper.cardIssuerCreateDTOtoCardIssuer(
                    cardIssuerCreateDTO
                )
            )
        )
    }

    override fun getAllCardIssuers(): List<CardIssuerDTO> {
        return cardIssuerRepository.findAll().stream().map(cardIssuerMapper::cardIssuerToCardIssuerDTO).toList()
    }

    override fun deleteCardIssuer(id: Long) {
        val cardIssuer: CardIssuer = cardIssuerRepository.findById(id)
            .orElseThrow { CardIssuerNotFoundException(id) }

        cardIssuerRepository.delete(cardIssuer)
    }
}