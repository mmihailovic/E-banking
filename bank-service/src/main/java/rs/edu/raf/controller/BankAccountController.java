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
import rs.edu.raf.dto.BankAccountDTO;
import rs.edu.raf.dto.BusinessBankAccountCreateDTO;
import rs.edu.raf.dto.CurrentBankAccountCreateDTO;
import rs.edu.raf.dto.ForeignCurrencyBankAccountCreateDTO;
import rs.edu.raf.security.JwtUtil;
import rs.edu.raf.service.BankAccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bank-accounts")
@Tag(name = "Bank accounts", description = "Managing bank accounts")
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
public class BankAccountController {
    private BankAccountService bankAccountService;
    private JwtUtil jwtUtil;

    @ApiOperation(value = "Create foreign currency bank account")
    @PostMapping("/foreign-currency")
    public ResponseEntity<BankAccountDTO> createForeignCurrencyBankAccount(
            @RequestBody @Valid ForeignCurrencyBankAccountCreateDTO foreignCurrencyBankAccountCreateDTO) {
        return new ResponseEntity<>(
                bankAccountService.createForeignCurrencyBankAccount(foreignCurrencyBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()), HttpStatus.OK
        );
    }

    @ApiOperation(value = "Create business bank account")
    @PostMapping("/business")
    public ResponseEntity<BankAccountDTO> createBusinessBankAccount(
            @RequestBody @Valid BusinessBankAccountCreateDTO businessBankAccountCreateDTO){
        return new ResponseEntity<>(bankAccountService.createBusinessBankAccount(businessBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()), HttpStatus.OK);
    }
    @ApiOperation(value = "Create current bank account")
    @PostMapping("/current")
    public ResponseEntity<BankAccountDTO> createCurrentBankAccount(
            @RequestBody @Valid CurrentBankAccountCreateDTO currentBankAccountCreateDTO){
        return new ResponseEntity<>(bankAccountService.createCurrentBankAccount(currentBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()), HttpStatus.OK);
    }
    @ApiOperation(value = "Get all bank accounts for client")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccountsForClient(@Parameter(name = "Client ID") @PathVariable("id") Long id){
        return new ResponseEntity<>(bankAccountService.getAllBankAccountsForOwner(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all bank account for logged client")
    @GetMapping("/client")
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccountsForLoggedClient(){
        return new ResponseEntity<>(bankAccountService.getAllBankAccountsForOwner(jwtUtil.getIDForLoggedUser()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get bank account with ID")
    @GetMapping("/account/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccountWithID(@Parameter(name = "Bank account ID") @PathVariable("id") Long id){
        return new ResponseEntity<>(bankAccountService.findActiveBankAccountByID(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get bank account by account number")
    @GetMapping("/account/number/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getBankAccountWithAccountNumber(@Parameter(name = "Bank account number")
                                                              @PathVariable("accountNumber") Long bankAccountNumber) {
        return new ResponseEntity<>(bankAccountService.findActiveBankAccountByAccountNumber(bankAccountNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "Deactivate bank account")
    @PutMapping("/deactivate/{accountNumber}")
    public ResponseEntity<Boolean> deactivateBankAccount(@Parameter(name = "Bank account number")
                                                             @PathVariable("accountNumber") Long accountNumber) {
        return new ResponseEntity<>(bankAccountService.deactivateBankAccount(accountNumber),HttpStatus.OK);
    }

    @ApiOperation(value = "Deducts available balance")
    @PutMapping("/deduct-available-balance/{owner}/{amount}")
    public ResponseEntity<Boolean> deductAvailableBalance(@Parameter(name = "Amount") @PathVariable("amount") BigDecimal amount,
            @Parameter(name = "Bank account owner ID") @PathVariable("owner") Long accountNumber) {
        return new ResponseEntity<>(bankAccountService.deductAvailableBalanceFromBankAccount(accountNumber, amount), HttpStatus.OK);
    }
}
