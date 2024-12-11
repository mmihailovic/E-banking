package rs.edu.raf.util;

import rs.edu.raf.exceptions.JMBGDateMismatchException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JMBGValidator {

    public static void validateJMBG(Long dateOfBirthTimestamp, String jmbg) {
        LocalDateTime dateOfBirth = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateOfBirthTimestamp), ZoneOffset.systemDefault());
        if (jmbg.length() == 12) {
            if (dateOfBirth.getDayOfMonth() != Integer.parseInt(jmbg.substring(0, 1)) ||
                    dateOfBirth.getMonthValue() != Integer.parseInt(jmbg.substring(1, 3)) ||
                    dateOfBirth.getYear() % 1000 != Integer.parseInt(jmbg.substring(3, 6))) {
                throw new JMBGDateMismatchException();
            }
        }
        else{
            if (dateOfBirth.getDayOfMonth() != Integer.parseInt(jmbg.substring(0, 2)) ||
                    dateOfBirth.getMonthValue() != Integer.parseInt(jmbg.substring(2, 4)) ||
                    dateOfBirth.getYear() % 1000 != Integer.parseInt(jmbg.substring(4, 7))) {
                throw new JMBGDateMismatchException();
            }
        }
    }
}
