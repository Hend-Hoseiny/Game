package exception;

public class CannotFieldException extends ActionException {
    
    //constructors
    public CannotFieldException() {
        super();
    }

    public CannotFieldException(String message) {
        super(message);
    }
    
}
