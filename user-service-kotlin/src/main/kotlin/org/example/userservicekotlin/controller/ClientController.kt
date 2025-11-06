package org.example.userservicekotlin.controller

import jakarta.validation.Valid
import org.example.userservicekotlin.dto.ClientCreateDTO
import org.example.userservicekotlin.dto.ClientDTO
import org.example.userservicekotlin.dto.ClientRegisterDTO
import org.example.userservicekotlin.dto.EditClientDTO
import org.example.userservicekotlin.security.JwtUtil
import org.example.userservicekotlin.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/clients")
class ClientController(val clientService: ClientService, val jwtUtil: JwtUtil) {
    @PostMapping("/add")
    fun createClient(@RequestBody clientCreateDTO: @Valid ClientCreateDTO): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.createNewClient(clientCreateDTO), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun editClient(
        @RequestBody editClientDTO: @Valid EditClientDTO,
        @PathVariable("id") clientId: Long
    ): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.editClient(editClientDTO, clientId), HttpStatus.OK)
    }

    @PutMapping
    fun editLoggedClient(@RequestBody editClientDTO: @Valid EditClientDTO): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(
            clientService.editClient(editClientDTO, jwtUtil.getIDForLoggedUser()),
            HttpStatus.OK
        )
    }

    @GetMapping
    fun getAllClients(): ResponseEntity<List<ClientDTO>> {
        return ResponseEntity<List<ClientDTO>>(clientService.getAllActiveClients(), HttpStatus.OK)
    }

    @GetMapping("/email/{email}")
    fun getClientByEmail(@PathVariable("email") email: String): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.getActiveClientByEmail(email), HttpStatus.OK)
    }

    @GetMapping("/jmbg/{jmbg}")
    fun getClientByJMBG(@PathVariable("jmbg") jmbg: String): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.getActiveClientByJMBG(jmbg), HttpStatus.OK)
    }

    @GetMapping("/phone-number/{phoneNumber}")
    fun getClientByPhoneNumber(@PathVariable("phoneNumber") phoneNumber: String): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.getActiveClientByPhoneNumber(phoneNumber), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun registerClient(@RequestBody clientRegisterDTO: @Valid ClientRegisterDTO): ResponseEntity<Boolean> {
        return ResponseEntity<Boolean>(clientService.registerNewClient(clientRegisterDTO), HttpStatus.OK)
    }

    @GetMapping("/id/{id}")
    fun getClientByID(@PathVariable("id") id: Long): ResponseEntity<ClientDTO> {
        return ResponseEntity<ClientDTO>(clientService.getActiveClientById(id), HttpStatus.OK)
    }

    @PutMapping("/{JMBG}/account/{accountNumber}")
    fun addAccountToClient(
        @PathVariable("JMBG") JMBG: String,
        @PathVariable("accountNumber") accountNumber: Long
    ): ResponseEntity<Long> {
        return ResponseEntity<Long>(clientService.addBankAccountToClient(JMBG, accountNumber), HttpStatus.OK)
    }

}