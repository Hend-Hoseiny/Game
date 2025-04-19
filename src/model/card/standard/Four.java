package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.board.BoardManager;
import engine.GameManager;

public class Four extends Standard {
    public Four(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 4, suit, boardManager, gameManager);
    }

    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Four requires exactly 1 marble");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Four can only move your own marble");
        }

        try {
            
            boardManager.moveBy(marbles.get(0), -4, false); 
        } catch (IllegalMovementException e) {
            throw new ActionException("Four movement blocked: " + e.getMessage(), e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("Four movement conflict: " + e.getMessage(), e);
        }
    }
    
}
