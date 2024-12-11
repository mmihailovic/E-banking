package rs.edu.raf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.*;

import rs.edu.raf.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*")
public class ClientController {
    private ClientService clientService;

    @PostMapping("/add")
    @Operation(description = "Create new client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientCreateDTO clientCreateDTO) {
        return new ResponseEntity<>(clientService.createNewClient(clientCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit client")
    public ResponseEntity<ClientDTO> editClient(@RequestBody @Valid EditClientDTO editClientDTO,
                                                @Parameter(name = "Client ID") @PathVariable("id") Long clientId) {
         return new ResponseEntity<>(clientService.editClient(editClientDTO, clientId), HttpStatus.OK);
    }

    @PutMapping
    @Operation(description = "Edit currently logged client")
    public ResponseEntity<ClientDTO> editLoggedClient(@RequestBody @Valid EditClientDTO editClientDTO,
                                                      @RequestAttribute("userId") Long clientId) {
        return new ResponseEntity<>(clientService.editClient(editClientDTO, clientId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllActiveClients(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @Operation(description = "Get client by email")
    public ResponseEntity<ClientDTO> getClientByEmail(@PathVariable("email") @Parameter(description = "Client email") String email) {
        return new ResponseEntity<>(clientService.getActiveClientByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/jmbg/{jmbg}")
    @Operation(description = "Get client by JMBG")
    public ResponseEntity<ClientDTO> getClientByJMBG(@PathVariable("jmbg") @Parameter(description = "JMBG") String jmbg) {
        return new ResponseEntity<>(clientService.getActiveClientByJMBG(jmbg), HttpStatus.OK);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    @Operation(description = "Get client by phone number")
    public ResponseEntity<ClientDTO> getClientByPhoneNumber(@Parameter(description = "Phone number")
                                                                @PathVariable("phoneNumber") String phoneNumber) {
        return new ResponseEntity<>(clientService.getActiveClientByPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(description = "Register client by checking registration code and setting password")
    public ResponseEntity<Boolean> registerClient(@RequestBody @Valid ClientRegisterDTO clientRegisterDTO) {
        return new ResponseEntity<>(clientService.registerNewClient(clientRegisterDTO), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Operation(description = "Get client by ID")
    public ResponseEntity<ClientDTO> getClientByID(@PathVariable("id") @Parameter(description = "Client ID") Long id) {
        return new ResponseEntity<>(clientService.getActiveClientById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/account/{accountNumber}")
    public ResponseEntity<Boolean> addAccountToClient(@PathVariable("id") Long id, @PathVariable("accountNumber") Long accountNumber) {
        return new ResponseEntity<>(clientService.addBankAccountToClient(id, accountNumber), HttpStatus.OK);
    }
}
