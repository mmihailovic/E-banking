package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CardDeactivatedException extends CustomException{
    public CardDeactivatedException(String cardNumber) {
        super("Card with number " + cardNumber + " is deactivated!", HttpStatus.BAD_REQUEST);
    }
}
