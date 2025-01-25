package wbb.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WBBExceptionTest {

    @Test
    public void testWBBExceptionMessage() {
        String errorMessage = "This is a test error message.";
        WBBException exception = new WBBException(errorMessage);
        assertNotNull(exception, "The exception object should not be null.");
        assertEquals(errorMessage, exception.getMessage(), "The error message should match the input message.");
    }

    @Test
    public void testWBBExceptionIsInstanceOfException() {
        String errorMessage = "Instance of Exception test.";
        WBBException exception = new WBBException(errorMessage);
        assertInstanceOf(Exception.class, exception, "WBBException should be an instance of Exception.");
    }

    @Test
    public void testWBBExceptionWithNullMessage() {
        WBBException exception = new WBBException(null);
        assertNull(exception.getMessage(), "The error message should be null when constructed with a null message.");
    }
}
