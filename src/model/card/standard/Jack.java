package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class Jack extends Standard {
    public Jack(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 11, suit, boardManager, gameManager);
    }
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 2) ; 
    }
    @Override

    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = GameManager.getActivePlayerColour(); //based on the assumption of default 1 marble
    	
    	if(marbles.size() == 1){
	    	if (playerColor.equals(givenColor)){
	    		return true;
	    	}
	    	else{
	    		return false;
	    	}
    	}
    	if (marbles.size() == 2){
    		Colour givenColor1 = marbles.get(0).getColor();
    		Colour givenColor2 = marbles.get(1).getColor();
    	 if (playerColor.equals(givenColor1)&& !(playerColor.equals(givenColor2)) ){
    		 return true;
    		 }
    	 if (playerColor.equals(givenColor2)&& !(playerColor.equals(givenColor1))){
    		 return true;
    	 }
    	 else{
    		 return false;
    	 }
    	 }
    }
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        // 1. Explicit validation
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Jack requires 1 or 2 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException(marbles.size() == 1 ? 
                "Jack can only move your own marble" : 
                "Swap requires one yours and one opponent's marble");
        }

        try {
            if (marbles.size() == 2) {
                
                boardManager.swap(marbles.get(0), marbles.get(1));
            } else {
              
                boardManager.moveBy(marbles.get(0), 11, false);
            }
        } catch (IllegalSwapException e) {
            throw new ActionException("Swap failed: " + e.getMessage(), e);
        } catch (IllegalMovementException e) {
            throw new ActionException("Movement failed: " + e.getMessage(), e);
        } catch (IllegalDestroyException e) {
            throw new ActionException("Movement conflict: " + e.getMessage(), e);
        }
    }
    

}
