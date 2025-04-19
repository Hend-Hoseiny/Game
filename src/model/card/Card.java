package model.card;
import model.Colour;
import engine.GameManager;
import engine.board.BoardManager;
import model.Colour;

public abstract class Card {
    // A class representing Cards in the game.
    private final String name;
    private final String description;
    protected BoardManager boardManager;
    protected GameManager gameManager;

    public Card(String name, String description, BoardManager boardManager, GameManager gameManager) {
        this.name = name;
        this.description = description;
        this.gameManager = gameManager;
        this.boardManager = boardManager;
    }
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return marbles.size() == 1; // most cards use 1 marble
    }
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = GameManager.getActivePlayerColour(); //based on the assumption of default 1 marble
    	Colour givenColor = marbles.get(0).getColor();
    	if (playerColor.equals(givenColor)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public abstract void act(ArrayList<Marble> marbles) throws ActionException,InvalidMarbleException;
    

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
