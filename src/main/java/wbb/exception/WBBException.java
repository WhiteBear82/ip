package wbb.exception;

/**
 * WinterBearBot Exception Class.
 */
//CHECKSTYLE.OFF: AbbreviationAsWordInName
public class WBBException extends Exception {
    //CHECKSTYLE.ON: AbbreviationAsWordInName
    /**
     * To construct the desired error message
     * @param message The error message
     */
    public WBBException(String message) {
        super(message);
    }
}
