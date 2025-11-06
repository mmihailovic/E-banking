package org.example.userservicekotlin.service

import org.example.userservicekotlin.dto.ClientCreateDTO
import org.example.userservicekotlin.dto.ClientDTO
import org.example.userservicekotlin.dto.ClientRegisterDTO
import org.example.userservicekotlin.dto.EditClientDTO

interface ClientService {
    /**
     * Creates a new client.
     *
     * @param clientCreateDTO The DTO containing information about the new user.
     * @return The [ClientDTO] object representing the created client.
     */
    fun createNewClient(clientCreateDTO: ClientCreateDTO): ClientDTO

    /**
     * Registers a new client.
     *
     * @param clientRegisterDTO The DTO containing information to register the new client.
     * @return True if the client is successfully registered, otherwise false.
     */
    fun registerNewClient(clientRegisterDTO: ClientRegisterDTO): Boolean

    /**
     * Edits a client.
     *
     * @param editClientDTO The DTO containing information to edit the client.
     * @param clientId ID of the client
     * @return The updated [ClientDTO] object.
     */
    fun editClient(editClientDTO: EditClientDTO, clientId: Long): ClientDTO

    /**
     * Lists all active clients.
     *
     * @return A list of [ClientDTO] objects representing all active clients.
     */
    fun getAllActiveClients(): List<ClientDTO>

    /**
     * Finds an active client by email.
     *
     * @param email The email address of the client to find.
     * @return The [ClientDTO] object representing the found client.
     */
    fun getActiveClientByEmail(email: String): ClientDTO

    /**
     * Finds an active client by JMBG.
     *
     * @param jmbg The JMBG of the client to find.
     * @return The [ClientDTO] object representing the found client.
     */
    fun getActiveClientByJMBG(jmbg: String): ClientDTO

    /**
     * Finds an active client by phone number.
     *
     * @param phoneNumber The phone number of the client to find.
     * @return The [ClientDTO] object representing the found client.
     */
    fun getActiveClientByPhoneNumber(phoneNumber: String): ClientDTO

    /**
     * Finds a client by ID.
     *
     * @param id The ID of the client to find.
     * @return The [ClientDTO] object representing the found client.
     */
    fun getActiveClientById(id: Long): ClientDTO

    /**
     * Adds an account to a client.
     *
     * @param JMBG JMBG of the client.
     * @param bankAccountNumber The bank account number to add.
     * @return ID of the client if the account is successfully added, otherwise null.
     */
    fun addBankAccountToClient(JMBG: String, bankAccountNumber: Long): Long?
}