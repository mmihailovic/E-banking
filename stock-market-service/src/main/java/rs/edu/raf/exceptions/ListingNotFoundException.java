package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class ListingNotFoundException extends CustomException{

    public ListingNotFoundException(Long id) {
        super("Listing with ID " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
    }
}
