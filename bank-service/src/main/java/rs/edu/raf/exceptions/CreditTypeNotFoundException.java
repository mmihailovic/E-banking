package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CreditTypeNotFoundException extends CustomException{
    public CreditTypeNotFoundException(Long id) {
        super("Credit type with ID " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
