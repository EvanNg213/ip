package chocolate.exception;

/**
 * Represents an error caused by invalid input.
 */
public class ChocolateException extends Exception {
    public ChocolateException(String message) {
        super(message);
    }
}
