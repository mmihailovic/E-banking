package rs.edu.raf.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.CompanyCreateDTO;
import rs.edu.raf.dto.CompanyDTO;
import rs.edu.raf.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
@Tag(name = "Company Controller", description = "Manage companies")
@SecurityRequirement(name = "jwt")
public class CompanyController {
    private CompanyService companyService;

    @GetMapping
    @ApiOperation("Get all companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get company with specified ID")
    public ResponseEntity<CompanyDTO> getCompanyById(@Parameter(name = "Company ID") @PathVariable("id") Long id) {
        return new ResponseEntity<>(companyService.findCompanyById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create company")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyCreateDTO companyCreateDTO) {
        return new ResponseEntity<>(companyService.createCompany(companyCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{TIN}/account/{accountNumber}")
    @ApiOperation("Add bank account to company")
    public ResponseEntity<Long> addAccountToCompany(@Parameter(name = "Company TIN") @PathVariable("TIN") Integer TIN,
                       @Parameter(name = "Bank account number") @PathVariable("accountNumber") Long accountNumber) {
        return new ResponseEntity<>(companyService.addBankAccountToCompany(TIN, accountNumber), HttpStatus.OK);
    }
}
