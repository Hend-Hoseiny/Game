package exception;

public abstract class GameException extends Exception {

    //constructors
    public GameException() {
         super(); }

    public GameException(String message) {
        super(message);
    }
    
}