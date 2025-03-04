package exception;

public abstract class InvalidSelectionException extends GameException{
    
    //constructors
    public InvalidSelectionException() {
        super();
    }

    public InvalidSelectionException(String message) {
        super(message);
    }

}
