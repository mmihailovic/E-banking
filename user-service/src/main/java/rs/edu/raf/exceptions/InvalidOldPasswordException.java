package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOldPasswordException extends CustomException{

    public InvalidOldPasswordException() {
        super("Current password isn't correct!", HttpStatus.BAD_REQUEST);
    }
}
