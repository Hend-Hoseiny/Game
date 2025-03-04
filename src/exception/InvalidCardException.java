package exception;

public class InvalidCardException extends InvalidSelectionException {
    
    //constructors
    public InvalidCardException() {
        super();
    }

    public InvalidCardException(String message) {
        super(message);
    }
}