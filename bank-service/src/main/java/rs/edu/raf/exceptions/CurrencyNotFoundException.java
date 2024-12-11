package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CurrencyNotFoundException extends CustomException{
    public CurrencyNotFoundException(Long id) {
        super("Currency with ID " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
