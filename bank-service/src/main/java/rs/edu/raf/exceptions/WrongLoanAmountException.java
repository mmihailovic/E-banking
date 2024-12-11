package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class WrongLoanAmountException extends CustomException{
    public WrongLoanAmountException(BigDecimal maxLoanAmount) {
        super("Loan amount can't be greater than " + maxLoanAmount, HttpStatus.BAD_REQUEST);
    }
}
