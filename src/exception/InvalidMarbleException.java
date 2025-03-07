package exception;

public class InvalidMarbleException extends InvalidSelectionException{
   
    //constructors
    public InvalidMarbleException() {
        super();
    }

    public InvalidMarbleException(String message) {
        super(message);
    }
    
}
