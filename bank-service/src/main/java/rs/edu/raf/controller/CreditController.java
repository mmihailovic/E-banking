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
import rs.edu.raf.dto.CreditDTO;
import rs.edu.raf.dto.CreditRequestCreateDto;
import rs.edu.raf.dto.CreditRequestDTO;
import rs.edu.raf.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/credit")
@AllArgsConstructor
@Tag(name = "Credits", description = "Managing credits")
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*")
public class CreditController {
    private final CreditService creditService;

    @ApiOperation(value = "Apply for credit")
    @PostMapping("/apply")
    public ResponseEntity<CreditRequestDTO> applyForCredit(@RequestBody @Valid CreditRequestCreateDto creditRequestCreateDto){
        return new ResponseEntity<>(creditService.applyForCredit(creditRequestCreateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Deny credit request")
    @PutMapping("/deny/{id}")
    public ResponseEntity<CreditRequestDTO> denyCreditRequest(@Parameter(name = "Credit request ID")
                                                                  @PathVariable("id") Long id) {
        return new ResponseEntity<>(creditService.dennyCreditRequest(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Approve credit request")
    @PutMapping("/approve/{id}")
    public ResponseEntity<CreditRequestDTO> approveCredit(@Parameter(name = "Credit request ID") @PathVariable("id") Long id){
        return new ResponseEntity<>(creditService.approveCreditRequest(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all credit requests with specified status")
    @GetMapping("/all/{status}")
    public ResponseEntity<List<CreditRequestDTO>> getAllCreditRequestsWithStatus(@Parameter(name = "Credit request status")
                                                                                    @PathVariable("status") String status){
        return new ResponseEntity<>(creditService.getAllCreditRequestsWithStatus(status), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all credit requests with specified status for user")
    @GetMapping("/{status}")
    public ResponseEntity<List<CreditRequestDTO>> getAllCreditRequestsForUserWithStatus(@RequestAttribute("userId") Long userId,
                                    @Parameter(name = "Credit request status") @PathVariable("status") String status) {
        return new ResponseEntity<>(creditService.getAllCreditRequestForUserWithStatus(userId, status), HttpStatus.OK);
    }

    @ApiOperation("Get details about credit")
    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCreditDetails(@Parameter(name = "Credit ID") @PathVariable("id") Long id){
        return new ResponseEntity<>(creditService.getDetailedCredit(id), HttpStatus.OK);
    }

}
