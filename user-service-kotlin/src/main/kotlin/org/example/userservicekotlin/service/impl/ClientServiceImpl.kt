package org.example.userservicekotlin.service.impl

import org.example.userservicekotlin.dto.ClientCreateDTO
import org.example.userservicekotlin.dto.ClientDTO
import org.example.userservicekotlin.dto.ClientRegisterDTO
import org.example.userservicekotlin.dto.EditClientDTO
import org.example.userservicekotlin.exception.UserNotFoundException
import org.example.userservicekotlin.mapper.ClientMapper
import org.example.userservicekotlin.model.code.CodeType
import org.example.userservicekotlin.model.user.Client
import org.example.userservicekotlin.repository.ClientRepository
import org.example.userservicekotlin.service.ClientService
import org.example.userservicekotlin.service.CodeService
import org.example.userservicekotlin.util.JMBGValidator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    val clientRepository: ClientRepository,
    val clientMapper: ClientMapper,
    val codeService: CodeService,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) : ClientService {
    override fun createNewClient(clientCreateDTO: ClientCreateDTO): ClientDTO {
        val client: Client = clientMapper.clientCreateDTOtoClient(clientCreateDTO)

        JMBGValidator.validateJMBG(client.dateOfBirth, client.JMBG)

        return clientMapper.clientToClientDTO(clientRepository.save(client))
    }

    override fun registerNewClient(clientRegisterDTO: ClientRegisterDTO): Boolean {
        if (codeService.checkCode(clientRegisterDTO.email, clientRegisterDTO.code, CodeType.REGISTRATION)) {
            val client: Client = clientMapper.clientRegisterDTOtoClient(clientRegisterDTO)
            if (client.accountNumbers!!.contains(clientRegisterDTO.bankAccountNumber)) {
                clientRepository.save(client)
                return true
            }
        }
        return false
    }

    override fun editClient(editClientDTO: EditClientDTO, clientId: Long): ClientDTO {
        val client: Client = clientRepository.findById(clientId)
            .orElseThrow { UserNotFoundException("User with ID $clientId not found!") }

        updateIfPresent({ client.lastName = it }, editClientDTO.lastName)
        updateIfPresent({ client.gender = it }, editClientDTO.gender)
        updateIfPresent({ client.email = it }, editClientDTO.email)
        updateIfPresent({ client.phoneNumber = it }, editClientDTO.phoneNumber)
        updateIfPresent({ client.address = it }, editClientDTO.address)
        updateIfPresent({ client.userPassword = it }, bCryptPasswordEncoder.encode(editClientDTO.password))
        client.active = editClientDTO.active

        return clientMapper.clientToClientDTO(clientRepository.save(client))
    }

    override fun getAllActiveClients(): List<ClientDTO> {
        return clientRepository.findAllByActiveIsTrue()
            .stream()
            .map(clientMapper::clientToClientDTO)
            .toList()
    }

    override fun getActiveClientByEmail(email: String): ClientDTO {
        return clientRepository.findByEmailAndActiveIsTrue(email)
            .map(clientMapper::clientToClientDTO)
            .orElseThrow { UserNotFoundException(email) }
    }

    override fun getActiveClientByJMBG(jmbg: String): ClientDTO {
        return clientRepository.findByJMBGAndActiveIsTrue(jmbg)
            .map(clientMapper::clientToClientDTO)
            .orElseThrow { UserNotFoundException("Client with JMBG $jmbg not found!") }
    }

    override fun getActiveClientByPhoneNumber(phoneNumber: String): ClientDTO {
        return clientRepository.findByPhoneNumberAndActiveIsTrue(phoneNumber)
            .map(clientMapper::clientToClientDTO)
            .orElseThrow { UserNotFoundException("User with phone $phoneNumber not found!") }
    }

    override fun getActiveClientById(id: Long): ClientDTO {
        return clientMapper.clientToClientDTO(
            clientRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow { UserNotFoundException("User with ID $id not found!") })
    }

    override fun addBankAccountToClient(JMBG: String, bankAccountNumber: Long): Long? {
        val client: Client = clientRepository.findByJMBGAndActiveIsTrue(JMBG)
            .orElseThrow { UserNotFoundException("User with JMBG $JMBG not found!") }

        if (client.accountNumbers == null) {
            client.accountNumbers = bankAccountNumber.toString()
            clientRepository.save(client)
            return client.id
        }

        if (client.accountNumbers!!.contains(bankAccountNumber.toString())) {
            return null
        }

        client.accountNumbers = client.accountNumbers + "," + bankAccountNumber
        clientRepository.save(client)
        return client.id
    }

    fun updateIfPresent(setter: (String) -> Unit, value: String?) {
        if (!value.isNullOrBlank()) setter(value)
    }
}