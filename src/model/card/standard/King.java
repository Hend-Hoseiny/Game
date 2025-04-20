package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.GameManager;
import engine.board.BoardManager;

public class King extends Standard {
    public King(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 13, suit, boardManager, gameManager);
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 0) ; 
    }
    
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("King requires exactly 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("The marble selected must be of your colour");
        }
        if (marbles.isEmpty()) {
            gameManager.fieldMarble();
        } else {
            boardManager.moveBy(marbles.get(0), 13, true); 
        }
      
    }

}
