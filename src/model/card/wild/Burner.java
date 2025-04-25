package model.card.wild;

import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Burner extends Wild {
    public Burner(String name, String description, BoardManager boardManager, GameManager gameManager) {
        super(name, description, boardManager, gameManager);
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
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
    	if(countUniqueColours(marbles)!=1)
            return false;
        if(containsColour(marbles, playerColor))
            return false;
        return checkValidColours(marbles);
    }
    
    // @Override
    // public void act(ArrayList<Marble> marbles) throws ActionException,InvalidMarbleException {
      
    //         if (!validateMarbleSize(marbles)) {
    //             throw new InvalidMarbleException("Burner requires exactly 1 marble");
    //         }
    //         if (!validateMarbleColours(marbles)) {
    //             throw new InvalidMarbleException("Burner can only target opponent marbles");
    //         }
    //         boardManager.destroyMarble(marbles.get(0));
      
    // }

    @Override
public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
    // Validate marble selection
    if (!validateMarbleSize(marbles)) {
        throw new InvalidMarbleException("Burner requires exactly 1 marble");
    }
    
    try {
        // Burner destroys the selected marble (bypasses all protections)
        boardManager.destroyMarble(marbles.get(0));
    } catch (Exception e) {
        throw new ActionException("Burner card failed to destroy marble"){};
    }
}

}
