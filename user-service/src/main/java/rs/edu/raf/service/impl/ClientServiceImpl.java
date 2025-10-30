package rs.edu.raf.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.edu.raf.annotations.GeneratedCrudOperation;
import rs.edu.raf.annotations.GeneratedExternalAPI;
import rs.edu.raf.dto.*;
import rs.edu.raf.exceptions.UserNotFoundException;

import rs.edu.raf.mapper.ClientMapper;
import rs.edu.raf.model.code.CodeType;
import rs.edu.raf.model.user.Client;
import rs.edu.raf.repository.ClientRepository;
import rs.edu.raf.service.ClientService;
import rs.edu.raf.service.CodeService;
import rs.edu.raf.util.JMBGValidator;

import java.util.List;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private CodeService codeService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ClientDTO createNewClient(ClientCreateDTO clientCreateDTO) {
        Client client = clientMapper.clientCreateDTOtoClient(clientCreateDTO);

        JMBGValidator.validateJMBG(client.getDateOfBirth(), client.getJMBG());

        return clientMapper.clientToClientDTO(clientRepository.save(client));
    }

    @GeneratedExternalAPI
    @Override
    public boolean registerNewClient(ClientRegisterDTO clientRegisterDTO) {

        if(codeService.checkCode(clientRegisterDTO.email(), clientRegisterDTO.code(), CodeType.REGISTRATION)) {
            Client client = clientMapper.clientRegisterDTOtoClient(clientRegisterDTO);
            if(client.getAccountNumbers().contains(clientRegisterDTO.bankAccountNumber())) {
                clientRepository.save(client);
                return true;
            }
        }
        return false;
    }

    @Override
    public ClientDTO editClient(EditClientDTO editClientDTO, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->new UserNotFoundException("User with ID " + clientId + " not found!"));

        updateIfPresent(client::setLastName, editClientDTO.lastName());
        updateIfPresent(client::setGender, editClientDTO.gender());
        updateIfPresent(client::setEmail, editClientDTO.email());
        updateIfPresent(client::setPhoneNumber, editClientDTO.phoneNumber());
        updateIfPresent(client::setAddress, editClientDTO.address());
        updateIfPresent(client::setPassword, bCryptPasswordEncoder.encode(editClientDTO.password()));
        client.setActive(editClientDTO.active());

        return clientMapper.clientToClientDTO(clientRepository.save(client));
    }

    @Override
    public List<ClientDTO> getAllActiveClients() {
        return clientRepository.findAllByActiveIsTrue()
                .stream()
                .map(clientMapper::clientToClientDTO)
                .toList();
    }

    @Override
    public ClientDTO getActiveClientByEmail(String email) {
        return clientRepository.findByEmailAndActiveIsTrue(email)
                .map(clientMapper::clientToClientDTO)
                .orElseThrow(()->new UserNotFoundException(email));
    }

    @Override
    public ClientDTO getActiveClientByJMBG(String jmbg) {
        return clientRepository.findByJMBGAndActiveIsTrue(jmbg)
                .map(clientMapper::clientToClientDTO)
                .orElseThrow(()->new UserNotFoundException("Client with JMBG " + jmbg + " not found!"));
    }

    @Override
    public ClientDTO getActiveClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumberAndActiveIsTrue(phoneNumber)
                .map(clientMapper::clientToClientDTO)
                .orElseThrow(()->new UserNotFoundException("User with phone " + phoneNumber + " not found!"));
    }

    @GeneratedCrudOperation
    @Override
    public ClientDTO getActiveClientById(Long id) {
        return clientMapper.clientToClientDTO(clientRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(()->new UserNotFoundException("User with ID " + id + " not found!")));
    }

    @Override
    public Long addBankAccountToClient(String JMBG, Long bankAccountNumber) {
        Client client = clientRepository.findByJMBGAndActiveIsTrue(JMBG)
                .orElseThrow(() -> new UserNotFoundException("User with JMBG " + JMBG + " not found!"));

        if(client.getAccountNumbers() == null){
            client.setAccountNumbers(bankAccountNumber.toString());
            clientRepository.save(client);
            return client.getId();
        }

        if(client.getAccountNumbers().contains(bankAccountNumber.toString())){
            return null;
        }

        client.setAccountNumbers(client.getAccountNumbers() + "," + bankAccountNumber);
        clientRepository.save(client);
        return client.getId();
    }

    private void updateIfPresent(Consumer<String> setter, String value) {
        if(StringUtils.isNotBlank(value))
            setter.accept(value);
    }
}

