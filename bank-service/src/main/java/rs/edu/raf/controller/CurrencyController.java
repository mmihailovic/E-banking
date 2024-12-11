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
import rs.edu.raf.dto.CurrencyCreateDTO;
import rs.edu.raf.dto.CurrencyDTO;
import rs.edu.raf.service.CurrencyService;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@Tag(name = "Currency", description = "Managing currencies")
@CrossOrigin("*")
@RequestMapping("/currency")
public class CurrencyController {
    private CurrencyService currencyService;

    @GetMapping
    @ApiOperation("Get all currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        return new ResponseEntity<>(currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create currency")
    public ResponseEntity<CurrencyDTO> createCurrency(@RequestBody @Valid CurrencyCreateDTO currencyCreateDTO) {
        return new ResponseEntity<>(currencyService.createCurrency(currencyCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete credit type")
    public ResponseEntity<?> deleteCurrency(@Parameter(name = "Currency ID") @PathVariable("id") Long id) {
        currencyService.deleteCurrency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


