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
import rs.edu.raf.dto.CountryCreateDTO;
import rs.edu.raf.dto.CountryDTO;
import rs.edu.raf.service.CountryService;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@Tag(name = "Country", description = "Managing countries")
@CrossOrigin("*")
@RequestMapping("/country")
public class CountryController {
    private CountryService countryService;

    @GetMapping
    @ApiOperation("Get all countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create country")
    public ResponseEntity<CountryDTO> createCurrency(@RequestBody @Valid CountryCreateDTO countryCreateDTO) {
        return new ResponseEntity<>(countryService.createCountry(countryCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete country")
    public ResponseEntity<?> deleteCountry(@Parameter(name = "Country ID") @PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



