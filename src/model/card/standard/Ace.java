package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class Ace extends Standard {
    public Ace(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 1, suit, boardManager, gameManager);
    }
    @Override
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 0) ; 
    }
    @Override
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = GameManager.getActivePlayerColour(); //based on the assumption of default 1 marble
    	
    	if(marbles.size() == 1){
    		Colour givenColor = marbles.get(0).getColor();
	    	if (playerColor.equals(givenColor)){
	    		return true;
	    	}
	    	else{
	    		return false;
	    	}
    	}
    	if (marbles.size() == 0){
    		return true
    	}
    	else{
    		return false
    	}
    	
    }
    @Override
    public 
    void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Ace requires exactly 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)){
        	 throw new InvalidMarbleException("Ace can only move your own marbles");
        }
        
        try {
        	
            if (marbles.isEmpty()) {
                // Fielding case
                gameManager.fieldMarble();
            } else {
                // Movement case (1 step forward)
                boardManager.moveBy(marbles.get(0), 1, false);
            }
        } catch (CannotFieldException e) {
            throw new ActionException("Cannot field marble: " + e.getMessage());
        } catch (IllegalMovementException e) {
            throw new ActionException("Invalid Ace movement: " + e.getMessage());
        } catch (IllegalDestroyException e) {
            throw new ActionException("Destroy conflict during Ace move: " + e.getMessage());
        }
    }

}
