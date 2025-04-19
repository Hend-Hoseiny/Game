package model.card.wild;

import engine.GameManager;
import engine.board.BoardManager;

public class Burner extends Wild {
    public Burner(String name, String description, BoardManager boardManager, GameManager gameManager) {
        super(name, description, boardManager, gameManager);
    }
    @Override
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = GameManager.getActivePlayerColour(); 
    	if(marbles.size() == 1){
    	Colour givenColor = marbles.get(0).getColor();
    	if (!playerColor.equals(givenColor){
    		return true;
    	}}
    	
    	else{
    		return false;
    	}
    }
    
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException,InvalidMarbleException {
        try {
            if (!validateMarbleSize(marbles)) {
                throw new ActionException("Burner requires exactly 1 marble");
            }
            if (!validateMarbleColours(marbles)) {
                throw new ActionException("Burner can only target opponent marbles");
            }
            
            boardManager.destroyMarble(marbles.get(0));
        } catch (InvalidMarbleException | IllegalDestroyException e) {
            throw new ActionException("Burn failed: " + e.getMessage(), e);
        }
    }


}
