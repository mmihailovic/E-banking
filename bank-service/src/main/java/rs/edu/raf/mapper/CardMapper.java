package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CardDTO;
import rs.edu.raf.dto.CreateCardDto;
import rs.edu.raf.model.card.Card;
import rs.edu.raf.model.card.CardIssuer;
import rs.edu.raf.model.card.CardStatus;
import rs.edu.raf.model.card.CardType;
import rs.edu.raf.repository.CardIssuerRepository;
import rs.edu.raf.repository.accounts.BankAccountRepository;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class CardMapper {
    private BankAccountMapper bankAccountMapper;
    private BankAccountRepository bankAccountRepository;
    private SecureRandom secureRandom;
    private CardIssuerRepository cardIssuerRepository;
    private CardIssuerMapper cardIssuerMapper;

    public Card createCartDtoToCart(CreateCardDto createCardDto) {
        Card card = new Card();

        CardIssuer cardIssuer = cardIssuerRepository.findById(createCardDto.issuerId()).orElseThrow();

        card.setType(CardType.valueOf(createCardDto.type()));
        card.setCardIssuer(cardIssuer);
        card.setName(createCardDto.name());
        card.setCreationDate(System.currentTimeMillis());

        long expirationDate = LocalDate.now().plusMonths(60).withDayOfMonth(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        card.setExpirationDate(expirationDate);
        card.setBankAccount(bankAccountRepository.findByAccountNumberAndActiveIsTrue(createCardDto.bankAccountNumber()).orElseThrow());
        card.setCvv(secureRandom.nextInt(900) + 100);
        card.setCardLimit(createCardDto.limit());
        card.setCardStatus(CardStatus.ACTIVE);
        card.setNumber(generateCardNumber(card));

        return card;
    }

    public CardDTO cardToCardDto(Card card) {
        return new CardDTO(card.getId(), card.getNumber(), card.getType().toString(),
                cardIssuerMapper.cardIssuerToCardIssuerDTO(card.getCardIssuer()), card.getName(), card.getCreationDate(),
                card.getExpirationDate(), bankAccountMapper.bankAccountToBankAccountDTO(card.getBankAccount()),
                card.getCvv(), card.getCardLimit(), card.getCardStatus().toString());
    }

    private String generateCardNumber(Card card){
        CardIssuer cardIssuer = card.getCardIssuer();
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append(cardIssuer.getMII());
        cardNumber.append(cardIssuer.getBIN());

        String bankAccountNumber = card.getBankAccount().getAccountNumber().toString();
        cardNumber.append(bankAccountNumber, bankAccountNumber.length() - 10, bankAccountNumber.length() - 2);

        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private int calculateLuhnCheckDigit(String number){
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }
}
