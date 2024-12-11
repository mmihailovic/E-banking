package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class ExchangeRateNotFoundException extends CustomException{
    public ExchangeRateNotFoundException(String currencyCode) {
        super("Exchange rate for currency with code " + currencyCode + " not found!", HttpStatus.NOT_FOUND);
    }
}
