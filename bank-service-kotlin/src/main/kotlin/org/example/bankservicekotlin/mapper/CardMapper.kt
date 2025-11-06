package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.model.card.Card
import org.example.bankservicekotlin.model.card.CardType
import org.example.bankservicekotlin.model.card.CardStatus
import org.example.bankservicekotlin.model.card.CardIssuer
import org.example.bankservicekotlin.dto.CreateCardDTO
import org.example.bankservicekotlin.dto.CardDTO
import org.example.bankservicekotlin.repository.CardIssuerRepository
import org.example.bankservicekotlin.repository.accounts.BankAccountRepository
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.LocalDate
import java.time.ZoneId

@Component
class CardMapper(
    private val bankAccountMapper: BankAccountMapper,
    private val bankAccountRepository: BankAccountRepository,
    private val secureRandom: SecureRandom,
    private val cardIssuerRepository: CardIssuerRepository,
    private val cardIssuerMapper: CardIssuerMapper
) {

    fun createCartDtoToCart(createCardDto: CreateCardDTO): Card {
        val cardIssuer: CardIssuer = cardIssuerRepository.findById(createCardDto.issuerId).orElseThrow()
        val cardType = CardType.valueOf(createCardDto.type)
        val number = generateCardNumber(cardIssuer, createCardDto.bankAccountNumber.toString())
        val expirationDate = LocalDate.now().plusMonths(60).withDayOfMonth(1)
            .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val bankAccount =
            bankAccountRepository.findByAccountNumberAndActiveIsTrue(createCardDto.bankAccountNumber).orElseThrow()
        val cvv = secureRandom.nextInt(900) + 100
        return Card(
            null,
            number,
            cardType,
            cardIssuer,
            createCardDto.name,
            System.currentTimeMillis(),
            expirationDate,
            bankAccount,
            cvv,
            createCardDto.limit,
            CardStatus.ACTIVE
        )
    }

    fun cardToCardDto(card: Card): CardDTO {
        return CardDTO(
            card.id, card.number, card.type.toString(),
            cardIssuerMapper.cardIssuerToCardIssuerDTO(card.cardIssuer), card.name, card.creationDate,
            card.expirationDate, bankAccountMapper.bankAccountToBankAccountDTO(card.bankAccount),
            card.cvv, card.cardLimit, card.cardStatus.toString()
        )
    }

    private fun generateCardNumber(cardIssuer: CardIssuer, bankAccountNumber: String): String {
        val cardNumber = StringBuilder()
        cardNumber.append(cardIssuer.MII)
        cardNumber.append(cardIssuer.BIN)

        cardNumber.append(bankAccountNumber, bankAccountNumber.length - 10, bankAccountNumber.length - 2)

        val checkDigit = calculateLuhnCheckDigit(cardNumber.toString())
        cardNumber.append(checkDigit)

        return cardNumber.toString()
    }

    private fun calculateLuhnCheckDigit(number: String): Int {
        var sum = 0
        var alternate = false

        for (i in number.length - 1 downTo 0) {
            var n = Character.getNumericValue(number[i])
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n -= 9
                }
            }
            sum += n
            alternate = !alternate
        }

        return (10 - (sum % 10)) % 10
    }
}