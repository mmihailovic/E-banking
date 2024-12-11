package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class StockDoesntExist extends CustomException{

    public StockDoesntExist(String ticker) {
        super("Ticker " + ticker + " doesn't exist!", HttpStatus.NOT_FOUND);
    }
}
