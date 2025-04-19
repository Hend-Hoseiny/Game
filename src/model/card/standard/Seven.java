package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.board.BoardManager;
import engine.GameManager;

public class Seven extends Standard {
    public Seven(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 7, suit, boardManager, gameManager);
    }
    @Override
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 2) ; 
    }
    @Override
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = GameManager.getActivePlayerColour(); //based on the assumption of default 1 marble
    	if (marbles.size() == 1){
    	Colour givenColor = marbles.get(0).getColour();
    	if (playerColor.equals(givenColor)){
    		return true;
    	}}
     	if (marbles.size() == 2){
        	Colour givenColor1 = marbles.get(0).getColour();
        	Colour givenColor2 = marbles.get(1).getColour();
        	if (playerColor.equals(givenColor1) && playerColor.equals(givenColor2)){
        		return true;
        	}
        	}
     	else{return false;}
    	
    }
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Seven requires 1 or 2 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Seven can only move your own marbles");
        }

        try {
            if (marbles.size() == 2) {
               
                boardManager.moveBy(marbles.get(0), splitDistance, false); // how do i decide the split distance?
                boardManager.moveBy(marbles.get(1), 7 - splitDistance, false);
            } else {
                
                boardManager.moveBy(marbles.get(0), 7 , false);
            }
        } catch (IllegalMovementException e) {
            throw new ActionException("Seven movement blocked: " + e.getMessage(), e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("Seven movement conflict: " + e.getMessage(), e);
        }
    }


}
