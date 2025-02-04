package wbb.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

//CHECKSTYLE.OFF: AbbreviationAsWordInName
public class WBBExceptionTest {
    //CHECKSTYLE.ON: AbbreviationAsWordInName
    @Test
    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    public void testWBBExceptionMessage() {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        String errorMessage = "This is a test error message.";
        WBBException exception = new WBBException(errorMessage);
        assertNotNull(exception, "The exception object should not be null.");
        assertEquals(errorMessage, exception.getMessage(), "The error message should match the input message.");
    }

    @Test
    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    public void testWBBExceptionIsInstanceOfException() {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        String errorMessage = "Instance of Exception test.";
        WBBException exception = new WBBException(errorMessage);
        assertInstanceOf(Exception.class, exception, "WBBException should be an instance of Exception.");
    }

    @Test
    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    public void testWBBExceptionWithNullMessage() {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        WBBException exception = new WBBException(null);
        assertNull(exception.getMessage(), "The error message should be null when constructed with a null message.");
    }
}
