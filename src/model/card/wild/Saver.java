package model.card.wild;
import java.util.ArrayList;
import exception.*;
import model.player.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Saver extends Wild {
    public Saver(String name, String description, BoardManager boardManager, GameManager gameManager) {
        super(name, description, boardManager, gameManager);
    }
    
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        boardManager.sendToSafe(marbles.get(0)); 
    }

}
