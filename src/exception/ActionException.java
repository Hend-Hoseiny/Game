package exception;

public abstract class ActionException extends GameException{
    
    //constructors
    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }
}
