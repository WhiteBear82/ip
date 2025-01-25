package wbb.exception;

/**
 * WinterBearBot Exception Class.
 */
public class WBBException extends Exception {
    /**
     * To construct the desired error message
     * @param message The error message
     */
    public WBBException(String message) {
        super(message);
    }
}
