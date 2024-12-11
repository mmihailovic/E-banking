package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends CustomException{
    public CardNotFoundException(String cardNumber) {
        super("Card with number " + cardNumber + " not found!", HttpStatus.NOT_FOUND);
    }
}
