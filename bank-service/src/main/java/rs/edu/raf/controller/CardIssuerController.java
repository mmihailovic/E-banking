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
import rs.edu.raf.dto.CardIssuerCreateDTO;
import rs.edu.raf.dto.CardIssuerDTO;
import rs.edu.raf.service.CardIssuerService;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@Tag(name = "Card issuer", description = "Managing card issuers")
@CrossOrigin("*")
@RequestMapping("/card-issuer")
public class CardIssuerController {
    private CardIssuerService cardIssuerService;

    @GetMapping
    @ApiOperation("Get all card issuers")
    public ResponseEntity<List<CardIssuerDTO>> getAllCardIssuers() {
        return new ResponseEntity<>(cardIssuerService.getAllCardIssuers(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create card issuer")
    public ResponseEntity<CardIssuerDTO> createCardIssuer(@RequestBody @Valid CardIssuerCreateDTO cardIssuerCreateDTO) {
        return new ResponseEntity<>(cardIssuerService.createCardIssuer(cardIssuerCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete card issuer")
    public ResponseEntity<?> deleteCardIssuer(@Parameter(name = "Card issuer ID") @PathVariable("id") Long id) {
        cardIssuerService.deleteCardIssuer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
