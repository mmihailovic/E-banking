package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.ChangePasswordWithCodeDTO;
import rs.edu.raf.dto.ClientDTO;
import rs.edu.raf.dto.ClientCreateDTO;
import rs.edu.raf.dto.ClientRegisterDTO;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.model.user.Client;
import rs.edu.raf.repository.ClientRepository;
import rs.edu.raf.repository.RoleRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientMapper {
    private ClientRepository clientRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public Client clientCreateDTOtoClient(ClientCreateDTO clientCreateDTO) {
        Client client = new Client();

        client.setFirstName(clientCreateDTO.firstName());
        client.setLastName(clientCreateDTO.lastName());
        client.setJMBG(clientCreateDTO.JMBG());
        client.setDateOfBirth(clientCreateDTO.dateOfBirth());
        client.setGender(clientCreateDTO.gender());
        client.setEmail(clientCreateDTO.email());
        client.setPhoneNumber(clientCreateDTO.phoneNumber());
        client.setAddress(clientCreateDTO.address());
        client.setActive(clientCreateDTO.active());
        client.getRoles().add(roleRepository.findByName("ROLE_CLIENT").get());

        return client;
    }

    public Client clientRegisterDTOtoClient(ClientRegisterDTO clientRegisterDTO) {

        Optional<Client> client = clientRepository.findByEmailAndActiveIsTrue(clientRegisterDTO.email());

        if (client.isPresent()){
            client.get().setPassword(bCryptPasswordEncoder.encode(clientRegisterDTO.password()));
            return client.get();
        }

        throw new UserNotFoundException(clientRegisterDTO.email());
    }

    public ClientDTO clientToClientDTO(Client client) {
        return new ClientDTO(client.getId(),
            client.getFirstName(),
            client.getLastName(),
            client.getJMBG(),
            client.getDateOfBirth(),
            client.getGender(),
            client.getEmail(),
            client.getPhoneNumber(),
            client.getAddress(),
            client.getAccountNumbers()
        );
    }

    public Client changePasswordDTOtoClient(ChangePasswordWithCodeDTO changePasswordWithCodeDTO) {
        Optional<Client> client = clientRepository.findByEmailAndActiveIsTrue(changePasswordWithCodeDTO.email());
        if(client.isPresent()) {
            client.get().setPassword(bCryptPasswordEncoder.encode(changePasswordWithCodeDTO.password()));
            return client.get();
        }
        throw new UserNotFoundException(changePasswordWithCodeDTO.email());
    }

}
