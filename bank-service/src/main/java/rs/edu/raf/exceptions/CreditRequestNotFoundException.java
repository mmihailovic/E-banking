package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CreditRequestNotFoundException extends CustomException{
    public CreditRequestNotFoundException(Long id) {
        super("Credit request with ID " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
