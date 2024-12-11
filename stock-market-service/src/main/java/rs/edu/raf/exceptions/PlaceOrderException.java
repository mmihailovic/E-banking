package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class PlaceOrderException extends CustomException{

    public PlaceOrderException() {
        super("Order can't be placed!", HttpStatus.BAD_REQUEST);
    }
}
