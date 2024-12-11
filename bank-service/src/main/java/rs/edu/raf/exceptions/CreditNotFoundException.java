package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CreditNotFoundException extends CustomException{
    public CreditNotFoundException(Long id) {
        super("Credit with ID " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
