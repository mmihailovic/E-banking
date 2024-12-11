package rs.edu.raf.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.CardDTO;
import rs.edu.raf.dto.CreateCardDto;
import rs.edu.raf.service.CardService;

import java.util.List;


@RestController
@RequestMapping("/cards")
@AllArgsConstructor
@Tag(name = "Cards", description = "Managing cards")
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*")
public class CardController {
    private final CardService cardService;

    @ApiOperation(value = "Create card")
    @PostMapping("/create")
    public ResponseEntity<CardDTO> createCard(@RequestBody @Valid CreateCardDto createCardDto){
        return new ResponseEntity<>(cardService.createCard(createCardDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Block card")
    @PatchMapping("/block/{cardNumber}")
    public ResponseEntity<?> blockCard(@Parameter(name = "Card number") @PathVariable("cardNumber") String cardNumber){
        cardService.blockCard(cardNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Unblock card")
    @PatchMapping("/unblock/{cardNumber}")
    public ResponseEntity<?> unblockCard(@Parameter(name = "Card number") @PathVariable("cardNumber") String cardNumber){
        cardService.unblockCard(cardNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Deactivate card")
    @PatchMapping("/deactivate/{cardNumber}")
    public ResponseEntity<?> deactivateCard(@Parameter(name = "Card number") @PathVariable("cardNumber") String cardNumber){
        cardService.deactivateCard(cardNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get all cards")
    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards(){
        return new ResponseEntity<>(cardService.getAllCards(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all cards for specified client")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<CardDTO>> getAllCardsForClient(@Parameter(name = "Client ID") @PathVariable("id") Long id){
        return new ResponseEntity<>(cardService.getAllCardsForUser(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get cards for currently logged client")
    @GetMapping("/client")
    public ResponseEntity<List<CardDTO>> getAllCardsForLoggedClient(@RequestAttribute("userId") Long id){
        return new ResponseEntity<>(cardService.getAllCardsForUser(id), HttpStatus.OK);
    }
}
