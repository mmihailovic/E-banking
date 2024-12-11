package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class WrongCreditLoanTermException extends CustomException{
    public WrongCreditLoanTermException(int minLoanTerm, int maxLoanTerm) {
        super("Credit loan must be between " + minLoanTerm + " and " + maxLoanTerm, HttpStatus.BAD_REQUEST);
    }
}
