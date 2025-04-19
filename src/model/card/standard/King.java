package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class King extends Standard {
    public King(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 13, suit, boardManager, gameManager);
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
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("King requires 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("King can only move own marbles");
        }

        try {
            if (marbles.isEmpty()) {
                
                gameManager.fieldMarble();
            } else {
              
                boardManager.moveBy(marbles.get(0), 13, true); 
            }
        } catch (CannotFieldException e) {
            throw new ActionException("King cannot field marble: " + e.getMessage(), e);
        } catch (IllegalMovementException e) {
            throw new ActionException("King movement blocked: " + e.getMessage(), e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("King destroy conflict: " + e.getMessage(), e);
        }
    }

}
