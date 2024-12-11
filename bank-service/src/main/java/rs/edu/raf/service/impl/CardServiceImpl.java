package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CardDTO;
import rs.edu.raf.dto.CreateCardDto;
import rs.edu.raf.exceptions.CardDeactivatedException;
import rs.edu.raf.exceptions.CardNotFoundException;
import rs.edu.raf.model.card.Card;
import rs.edu.raf.mapper.CardMapper;
import rs.edu.raf.model.card.CardStatus;
import rs.edu.raf.repository.CardRepository;
import rs.edu.raf.service.CardService;

import java.util.List;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public CardDTO createCard(CreateCardDto createCardDto){
        return cardMapper.cardToCardDto(cardRepository.save(cardMapper.createCartDtoToCart(createCardDto)));
    }

    @Override
    public void deleteCard(String cardNumber){
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(()->new CardNotFoundException(cardNumber));
        cardRepository.delete(card);
    }

    @Override
    public void blockCard(String cardNumber){
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(()->new CardNotFoundException(cardNumber));

        if(card.getCardStatus().equals(CardStatus.DEACTIVATED))
            throw new CardDeactivatedException(cardNumber);

        card.setCardStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
    }

    @Override
    public void unblockCard(String cardNumber) {
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(()->new CardNotFoundException(cardNumber));

        if(card.getCardStatus().equals(CardStatus.DEACTIVATED))
            throw new CardDeactivatedException(cardNumber);

        card.setCardStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    public void deactivateCard(String cardNumber){
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(()->new CardNotFoundException(cardNumber));

        card.setCardStatus(CardStatus.DEACTIVATED);
        cardRepository.save(card);
    }

    @Override
    public List<CardDTO> getAllCards(){
        return cardRepository.findAll().stream().map(cardMapper::cardToCardDto).toList();
    }

    @Override
    public List<CardDTO> getAllCardsForUser(Long userId){
        return cardRepository.findByBankAccount_Owner(userId).stream().map(cardMapper::cardToCardDto).toList();
    }

}
