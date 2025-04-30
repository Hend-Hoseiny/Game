package model.card.standard;
import java.util.ArrayList;
import exception.*;
import model.Colour;
import model.player.*;
import engine.board.BoardManager;
import engine.GameManager;

public class Five extends Standard {
    public Five(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 5, suit, boardManager, gameManager);
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------------------------

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
        return checkValidColours(marbles);
    }


    @Override
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
       
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Five requires exactly 1 marble");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Invalid marble color");
        }
            
        boardManager.moveBy(marbles.get(0), 5 , false);
   
    }

}
