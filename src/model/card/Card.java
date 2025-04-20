package model.card;
import engine.GameManager;
import engine.board.BoardManager;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;

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

    // --------------------------------------------------------------------------------------------------------------------------------------
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return marbles.size() == 1; // most cards use 1 marble
    }
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = gameManager.getActivePlayerColour(); 
    	for(int i=0 ; i<marbles.size() ; i++){
            if(marbles.get(i).getColour()!=playerColor)
                return false;
        }
        return true;
    }
    
    public abstract void act(ArrayList<Marble> marbles) throws ActionException,InvalidMarbleException;
    

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}