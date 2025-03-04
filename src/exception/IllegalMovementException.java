package exception;

public class IllegalMovementException extends ActionException {
    
    //constructors
    public IllegalMovementException() {
        super();
    }

    public IllegalMovementException(String message) {
        super(message);
    }
    
}
