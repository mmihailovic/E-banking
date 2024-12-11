package rs.edu.raf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.*;
import rs.edu.raf.model.code.CodeType;
import rs.edu.raf.service.CodeService;
import rs.edu.raf.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User controller", description = "Operations for managing accounts")
@CrossOrigin("*")
public class UserController {
    private UserService userService;
    private CodeService codeService;

    @PutMapping("/change-password")
    @Operation(description = "Change password")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return new ResponseEntity<>(userService.changeUserPassword(changePasswordDTO), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @Operation(description = "Reset password with code")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ChangePasswordWithCodeDTO changePasswordWithCodeDTO) {
        return new ResponseEntity<>(userService.changeUserPasswordWithCode(changePasswordWithCodeDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(description = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully login"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/generate/{type}")
    @Operation(description = "Generate code")
    public ResponseEntity<?> generateCode(@RequestBody @Valid GenerateCodeDTO generateCodeDTO,
                                          @Parameter(name = "Code type") @PathVariable("type") String type) {
        codeService.addCode(generateCodeDTO.email(), type);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{email}/code/{code}")
    @Operation(description = "Check payment code")
    public ResponseEntity<Boolean> checkPaymentCode(@PathVariable("code") @Parameter(name = "Code") String code,
                                                    @PathVariable("email") @Parameter(name = "User email") String email) {
        return new ResponseEntity<>(codeService.checkCode(email, code, CodeType.PAYMENT_CODE), HttpStatus.OK);
    }
}
