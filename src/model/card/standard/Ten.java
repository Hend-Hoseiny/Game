package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.board.BoardManager;
import engine.GameManager;

public class Ten extends Standard {
    public Ten(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 10, suit, boardManager, gameManager);
    }
    @Override
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 0) ; // most cards use 1 marble
    }
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Ten requires 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Ten can only move your marbles");
        }
        if (marbles.isEmpty()) {
            Colour nextPlayer = gameManager.getNextPlayerColour();
            gameManager.discardCard(nextPlayer); 
        } else {
            boardManager.moveBy(marbles.get(0), 10 , false);
        }
  
    }
    

}
