package rs.edu.raf.exceptions;

import org.springframework.http.HttpStatus;

public class JMBGDateMismatchException extends CustomException{

        public JMBGDateMismatchException() {
            super("The date of birth and JMBG do not match!", HttpStatus.BAD_REQUEST);
        }
}
