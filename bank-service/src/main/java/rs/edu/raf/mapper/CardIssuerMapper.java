package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CardIssuerCreateDTO;
import rs.edu.raf.dto.CardIssuerDTO;
import rs.edu.raf.model.card.CardIssuer;

@Component
public class CardIssuerMapper {

    public CardIssuer cardIssuerCreateDTOtoCardIssuer(CardIssuerCreateDTO cardIssuerCreateDTO) {
        CardIssuer cardIssuer = new CardIssuer();

        cardIssuer.setName(cardIssuerCreateDTO.name());
        cardIssuer.setBIN(cardIssuerCreateDTO.BIN());
        cardIssuer.setMII(cardIssuerCreateDTO.MII());

        return cardIssuer;
    }

    public CardIssuerDTO cardIssuerToCardIssuerDTO(CardIssuer cardIssuer) {
        return new CardIssuerDTO(cardIssuer.getId(), cardIssuer.getName(), cardIssuer.getMII(), cardIssuer.getBIN());
    }
}
