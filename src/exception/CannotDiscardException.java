package exception;

public class CannotDiscardException extends ActionException {
    
    //constructors
    public CannotDiscardException() {
        super();
    }

    public CannotDiscardException(String message) {
        super(message);
    }
    
}
