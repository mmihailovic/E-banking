package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.FavouriteRecipientDTO;
import rs.edu.raf.dto.FavouriteRecipientRequestDTO;
import rs.edu.raf.model.FavouriteRecipient;
import rs.edu.raf.model.user.Client;

@Component
@AllArgsConstructor
public class FavouriteRecipientMapper {
    private ClientMapper clientMapper;

    public FavouriteRecipientDTO favouriteRecipientToFavouriteRecipientDTO(FavouriteRecipient source) {
        return new FavouriteRecipientDTO(source.getId(), clientMapper.clientToClientDTO(source.getClient()), source.getRecipientName(), source.getRecipientAccountNumber());
    }

    public FavouriteRecipient favouriteRecipientRequestDTOtoFavouriteRecipient(FavouriteRecipientRequestDTO source, Client client) {
        return new FavouriteRecipient(client, source.recipientName(), source.recipientAccountNumber());
    }

}
