package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class Queen extends Standard {
    public Queen(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 12, suit, boardManager, gameManager);
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
            throw new InvalidMarbleException("Queen requires 0 or 1 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Queen can only move your own marbles");
        }
        try {
        	if (marbles.isEmpty()) {
        		gameManager.discardCard();
        	}
        	else {
               
                boardManager.moveBy(marbles.get(0),12 , false);
            }
        	
        }
        
        catch (CannotDiscardException e) {
            throw new ActionException("Queen cannot discard card", e);
        } catch (IllegalMovementException e) {
            throw new ActionException("Queen movement blocked", e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("Queen movement conflict", e);
   }}
    }
