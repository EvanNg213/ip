package chocolate.exception;

/** Error handling for incorrect inputs */

public class ChocolateException extends Exception {
    public ChocolateException(String message) {
        super(message);
    }
}
