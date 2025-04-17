package model.card.standard;

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
            throw new InvalidMarbleException("Ten requires 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Ten can only move your marbles");
        }

        try {
            if (marbles.isEmpty()) {
              
                Colour nextPlayer = gameManager.getNextPlayerColour();
                gameManager.discardCard(nextPlayer); 
            } else {
     
                boardManager.moveBy(marbles.get(0), 10 , false);
            }
        } catch (CannotDiscardException e) {
            throw new ActionException("Cannot discard from next player", e);
        } catch (IllegalMovementException e) {
            throw new ActionException("Ten movement blocked", e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("Ten movement conflict", e);
        }
    }
    

}
