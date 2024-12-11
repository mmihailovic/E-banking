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
import rs.edu.raf.dto.CreditTypeCreateDTO;
import rs.edu.raf.dto.CreditTypeDTO;
import rs.edu.raf.service.CreditTypeService;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@Tag(name = "Credit type", description = "Managing credit types")
@CrossOrigin("*")
@RequestMapping("/credit-type")
public class CreditTypeController {
    private CreditTypeService creditTypeService;

    @GetMapping
    @ApiOperation("Get all credit types")
    public ResponseEntity<List<CreditTypeDTO>> getAllCreditTypes() {
        return new ResponseEntity<>(creditTypeService.getAllCreditTypes(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create credit type")
    public ResponseEntity<CreditTypeDTO> createCreditType(@RequestBody @Valid CreditTypeCreateDTO creditTypeCreateDTO) {
        return new ResponseEntity<>(creditTypeService.createCreditType(creditTypeCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete credit type")
    public ResponseEntity<?> deleteCreditType(@Parameter(name = "Credit type ID") @PathVariable("id") Long id) {
        creditTypeService.deleteCreditType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

