package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CardIssuerCreateDTO;
import rs.edu.raf.dto.CardIssuerDTO;
import rs.edu.raf.exceptions.CardIssuerNotFoundException;
import rs.edu.raf.mapper.CardIssuerMapper;
import rs.edu.raf.model.card.CardIssuer;
import rs.edu.raf.repository.CardIssuerRepository;
import rs.edu.raf.service.CardIssuerService;

import java.util.List;

@Service
@AllArgsConstructor
public class CardIssuerServiceImplementation implements CardIssuerService {
    private CardIssuerRepository cardIssuerRepository;
    private CardIssuerMapper cardIssuerMapper;

    @Override
    public CardIssuerDTO createCardIssuer(CardIssuerCreateDTO cardIssuerCreateDTO) {
        return cardIssuerMapper.cardIssuerToCardIssuerDTO(cardIssuerRepository.save(cardIssuerMapper.cardIssuerCreateDTOtoCardIssuer(cardIssuerCreateDTO)));
    }

    @Override
    public List<CardIssuerDTO> getAllCardIssuers() {
        return cardIssuerRepository.findAll().stream().map(cardIssuerMapper::cardIssuerToCardIssuerDTO).toList();
    }

    @Override
    public void deleteCardIssuer(Long id) {
        CardIssuer cardIssuer = cardIssuerRepository.findById(id)
                .orElseThrow(()-> new CardIssuerNotFoundException(id));

        cardIssuerRepository.delete(cardIssuer);
    }
}
