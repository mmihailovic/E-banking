package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.annotations.GeneratedCrudOperation;
import rs.edu.raf.dto.FavouriteRecipientDTO;
import rs.edu.raf.dto.FavouriteRecipientRequestDTO;
import rs.edu.raf.model.user.Client;
import rs.edu.raf.repository.FavouriteRecipientRepository;
import rs.edu.raf.mapper.FavouriteRecipientMapper;
import rs.edu.raf.security.JwtUtil;
import rs.edu.raf.service.FavouriteRecipientService;

import java.util.List;

@Service
@AllArgsConstructor
public class FavouriteRecipientServiceImpl implements FavouriteRecipientService {

    private FavouriteRecipientRepository favouriteRecipientRepository;
    private FavouriteRecipientMapper favouriteRecipientMapper;
    private JwtUtil jwtUtil;
    @Override
    @GeneratedCrudOperation
    public FavouriteRecipientDTO addFavouriteClient(FavouriteRecipientRequestDTO favouriteRecipientRequestDTO) {
        return favouriteRecipientMapper.favouriteRecipientToFavouriteRecipientDTO(favouriteRecipientRepository.save(favouriteRecipientMapper.favouriteRecipientRequestDTOtoFavouriteRecipient(favouriteRecipientRequestDTO, (Client) jwtUtil.getCurrentUser())));
    }

    @Override
    @GeneratedCrudOperation
    public FavouriteRecipientDTO editFavouriteClient(FavouriteRecipientRequestDTO favouriteRecipientRequestDTO) {
        return favouriteRecipientMapper.favouriteRecipientToFavouriteRecipientDTO(favouriteRecipientRepository.save(favouriteRecipientMapper.favouriteRecipientRequestDTOtoFavouriteRecipient(favouriteRecipientRequestDTO, (Client) jwtUtil.getCurrentUser())));
    }

    @Override
    @GeneratedCrudOperation
    public void deleteFavouriteClient(Long id) {
        favouriteRecipientRepository.deleteById(id);
    }

    @Override
    @GeneratedCrudOperation
    public List<FavouriteRecipientDTO> findFavouriteClientsForClient() {
        return favouriteRecipientRepository.findByClient_Id(jwtUtil.getCurrentUser().getId()).stream().map(favouriteRecipientMapper::favouriteRecipientToFavouriteRecipientDTO).toList();
    }
}
