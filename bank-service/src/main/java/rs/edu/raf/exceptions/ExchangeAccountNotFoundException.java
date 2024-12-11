package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class ExchangeAccountNotFoundException extends CustomException{
    public ExchangeAccountNotFoundException(String currencyCode) {
        super("Exchange account for currency with code " + currencyCode + " not found!", HttpStatus.NOT_FOUND);
    }
}
