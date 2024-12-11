package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class CountryNotFoundException extends CustomException{
    public CountryNotFoundException(Long id) {
        super("Country with ID " + id + " not found!", HttpStatus.NOT_FOUND);
    }
}
