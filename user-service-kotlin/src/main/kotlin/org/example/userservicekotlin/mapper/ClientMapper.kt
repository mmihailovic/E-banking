package org.example.userservicekotlin.mapper

import org.example.userservicekotlin.dto.ClientCreateDTO
import org.example.userservicekotlin.dto.ClientDTO
import org.example.userservicekotlin.dto.ClientRegisterDTO
import org.example.userservicekotlin.dto.ChangePasswordWithCodeDTO
import org.example.userservicekotlin.model.user.Client
import org.example.userservicekotlin.repository.ClientRepository
import org.example.userservicekotlin.repository.RoleRepository
import org.example.userservicekotlin.exception.UserNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientMapper(
    val clientRepository: ClientRepository,
    val roleRepository: RoleRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun clientCreateDTOtoClient(clientCreateDTO: ClientCreateDTO): Client {
        return Client(
            null,
            clientCreateDTO.phoneNumber,
            clientCreateDTO.address,
            clientCreateDTO.firstName,
            clientCreateDTO.lastName,
            clientCreateDTO.JMBG,
            clientCreateDTO.dateOfBirth,
            clientCreateDTO.gender,
            clientCreateDTO.email,
            null,
            clientCreateDTO.active,
            mutableListOf(roleRepository.findByName("ROLE_CLIENT").get()),
            null
        )
    }

    fun clientRegisterDTOtoClient(clientRegisterDTO: ClientRegisterDTO): Client {
        val client: Optional<Client> = clientRepository.findByEmailAndActiveIsTrue(clientRegisterDTO.email)

        if (client.isPresent) {
            client.get().userPassword = bCryptPasswordEncoder.encode(clientRegisterDTO.password)
            return client.get()
        }

        throw UserNotFoundException(clientRegisterDTO.email)
    }

    fun clientToClientDTO(client: Client): ClientDTO {
        return ClientDTO(
            client.id,
            client.firstName,
            client.lastName,
            client.JMBG,
            client.dateOfBirth,
            client.gender,
            client.email,
            client.phoneNumber,
            client.address,
            client.accountNumbers
        )
    }

    fun changePasswordDTOtoClient(changePasswordWithCodeDTO: ChangePasswordWithCodeDTO): Client {
        val client: Optional<Client> = clientRepository.findByEmailAndActiveIsTrue(changePasswordWithCodeDTO.email)
        if (client.isPresent) {
            client.get().userPassword = bCryptPasswordEncoder.encode(changePasswordWithCodeDTO.password)
            return client.get()
        }
        throw UserNotFoundException(changePasswordWithCodeDTO.email)
    }
}