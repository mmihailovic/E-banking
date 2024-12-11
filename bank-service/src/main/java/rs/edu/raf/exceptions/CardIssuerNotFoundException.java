package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CardIssuerNotFoundException extends CustomException{
    public CardIssuerNotFoundException(Long id) {
        super("Card issuer with " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
