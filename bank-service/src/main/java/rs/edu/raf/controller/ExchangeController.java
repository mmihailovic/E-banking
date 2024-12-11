package rs.edu.raf.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.ExchangeInvoiceDTO;
import rs.edu.raf.dto.ExchangeRateDto;
import rs.edu.raf.service.ExchangeInvoiceService;
import rs.edu.raf.service.ExchangeRateService;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*")
@Tag(name = "Exchange", description = "Exchange functionalities")
public class ExchangeController {
    private final ExchangeRateService exchangeRateService;
    private final ExchangeInvoiceService exchangeInvoiceService;

    @ApiOperation(value = "Get all currency rates")
    @GetMapping
    public ResponseEntity<List<ExchangeRateDto>> getAllCurrencyRates(){
        return new ResponseEntity<>(exchangeRateService.getAllExchangeRates(), HttpStatus.OK) ;
    }

    @GetMapping("/invoices/{currency}")
    @ApiOperation(value = "All invoices by currency")
    public ResponseEntity<List<ExchangeInvoiceDTO>> allInvoicesByCurrency(@PathVariable("currency") String currency) {
        return new ResponseEntity<>(exchangeInvoiceService.listInvoicesByCurrency(currency),HttpStatus.OK);
    }

}
