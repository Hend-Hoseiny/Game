package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Jack extends Standard {
    public Jack(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 11, suit, boardManager, gameManager);
    }

    // ------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	return (marbles.size() == 1)|| (marbles.size() == 2) ; 
    }

    private int countUniqueColours(ArrayList<Marble> marbles){
        ArrayList<Marble> temp = new ArrayList<>();
        for(int i=0 ; i<marbles.size() ; i++){
            if(!containsColour(temp, marbles.get(i).getColour()))
                temp.add(marbles.get(i));
        }
        return temp.size();
    }

    private boolean containsColour(ArrayList<Marble> marbles , Colour colour){
        for(int i =0 ; i<marbles.size(); i++){
            if(marbles.get(i).getColour()==colour)
                return true;
        }
        return false;
    }

    private boolean checkValidColours(ArrayList<Marble> marbles){
        ArrayList<Colour> colours = new ArrayList<>();
        colours.add(Colour.BLUE);
        colours.add(Colour.GREEN);
        colours.add(Colour.RED);
        colours.add(Colour.YELLOW);

        for(Marble marble:marbles){
            if(!colours.contains(marble.getColour()))
                return false;
        }
        return true;
    }

    @Override
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	Colour playerColor = gameManager.getActivePlayerColour(); 
        if(marbles.size()==1 && marbles.get(0).getColour()==playerColor)
            return true;
    	if(countUniqueColours(marbles)==2 && containsColour(marbles, playerColor) && checkValidColours(marbles))
            return true;
        return false;
    }
    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        // 1. Explicit validation
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Jack requires exactly 1 or 2 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException(marbles.size() == 1 ? 
                "Jack can only move your own marble" : 
                "Swap requires one yours and one opponent's marble");
        }

        if (marbles.size() == 2) {
            boardManager.swap(marbles.get(0), marbles.get(1));
        } else {
            boardManager.moveBy(marbles.get(0), 11, false);
        }
 
    }
    

}
 