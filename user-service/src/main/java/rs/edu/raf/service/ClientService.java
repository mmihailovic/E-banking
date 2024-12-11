package rs.edu.raf.service;

import rs.edu.raf.dto.*;

import java.util.List;

/**
 * This interface defines methods for managing users.
 */
public interface ClientService {

    /**
     * Creates a new client.
     *
     * @param clientCreateDTO The DTO containing information about the new user.
     * @return The {@link ClientDTO} object representing the created client.
     */
    ClientDTO createNewClient(ClientCreateDTO clientCreateDTO);

    /**
     * Registers a new client.
     *
     * @param clientRegisterDTO The DTO containing information to register the new client.
     * @return True if the client is successfully registered, otherwise false.
     */
    boolean registerNewClient(ClientRegisterDTO clientRegisterDTO);

    /**
     * Edits a client.
     *
     * @param editClientDTO The DTO containing information to edit the client.
     * @param clientId ID of the client
     * @return The updated {@link ClientDTO} object.
     */
    ClientDTO editClient(EditClientDTO editClientDTO, Long clientId);

    /**
     * Lists all active clients.
     *
     * @return A list of {@link ClientDTO} objects representing all active clients.
     */
    List<ClientDTO> getAllActiveClients();

    /**
     * Finds an active client by email.
     *
     * @param email The email address of the client to find.
     * @return The {@link ClientDTO} object representing the found client.
     */
    ClientDTO getActiveClientByEmail(String email);

    /**
     * Finds an active client by JMBG.
     *
     * @param jmbg The JMBG of the client to find.
     * @return The {@link ClientDTO} object representing the found client.
     */
    ClientDTO getActiveClientByJMBG(String jmbg);

    /**
     * Finds an active client by phone number.
     *
     * @param phoneNumber The phone number of the client to find.
     * @return The {@link ClientDTO} object representing the found client.
     */
    ClientDTO getActiveClientByPhoneNumber(String phoneNumber);

    /**
     * Finds a client by ID.
     *
     * @param id The ID of the client to find.
     * @return The {@link ClientDTO} object representing the found client.
     */
    ClientDTO getActiveClientById(Long id);

    /**
     * Adds an account to a client.
     *
     * @param clientId        The ID of the client.
     * @param bankAccountNumber The bank account number to add.
     * @return True if the account is successfully added, otherwise false.
     */
    boolean addBankAccountToClient(Long clientId, Long bankAccountNumber);
}
