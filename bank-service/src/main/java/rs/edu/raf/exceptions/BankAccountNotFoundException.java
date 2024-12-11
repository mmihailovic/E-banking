package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class BankAccountNotFoundException extends CustomException{

    public BankAccountNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
