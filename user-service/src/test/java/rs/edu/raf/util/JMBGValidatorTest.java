package rs.edu.raf.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.edu.raf.exceptions.JMBGDateMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JMBGValidatorTest {

    @Test
    void testValidJMBG12() {
        Long dateOfBirthTimestamp = LocalDateTime.of(1985, 2, 9, 0, 0, 0, 0)
                .atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        String validJMBG = "902985523123";

        assertDoesNotThrow(() -> JMBGValidator.validateJMBG(dateOfBirthTimestamp, validJMBG));
    }

    @Test
    void testInvalidJMBG12() {
        Long dateOfBirthTimestamp = LocalDateTime.of(1985, 2, 9, 0, 0, 0, 0)
                .atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        String invalidJMBG = "802985123123";

        assertThrows(JMBGDateMismatchException.class, () -> JMBGValidator.validateJMBG(dateOfBirthTimestamp, invalidJMBG));
    }

    @Test
    void testValidJMBG13() {
        Long dateOfBirthTimestamp = LocalDateTime.of(1985, 2, 15, 0, 0, 0, 0)
                .atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        String validJMBG = "1502985123123";

        assertDoesNotThrow(() -> JMBGValidator.validateJMBG(dateOfBirthTimestamp, validJMBG));
    }

    @Test
    void testInvalidJMBG13() {
        Long dateOfBirthTimestamp = LocalDateTime.of(1985, 2, 15, 0, 0, 0, 0)
                .atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();

        String invalidJMBG = "1602985123123";

        assertThrows(JMBGDateMismatchException.class, () -> JMBGValidator.validateJMBG(dateOfBirthTimestamp, invalidJMBG));
    }
}