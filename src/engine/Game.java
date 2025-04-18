package engine;

import engine.board.*;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Marble;
import model.player.Player;

import java.io.*;
import java.util.*;

public class Game implements GameManager {

    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Card> firePit;
    private int currentPlayerIndex;
    private int turn;

    private static final ArrayList<Colour> generateColourOrder() {
        ArrayList<Colour> colourOrder = new ArrayList<Colour>();
        Colour blue = Colour.BLUE;
        Colour green = Colour.GREEN;
        Colour red = Colour.RED;
        Colour yellow = Colour.YELLOW;
        colourOrder.add(blue);
        colourOrder.add(green);
        colourOrder.add(red);
        colourOrder.add(yellow);
        Collections.shuffle(colourOrder);
        return colourOrder;
    }

    public Game(String playerName) throws IOException {
        ArrayList<Colour> colourOrder = generateColourOrder();
        this.board = new Board(colourOrder, this);
        Deck.loadCardPool(board, this);

        players = new ArrayList<Player>();
        Colour colourA = colourOrder.get(0);
        Colour colourB = colourOrder.get(1);
        Colour colourC = colourOrder.get(2);
        Colour colourD = colourOrder.get(3);

        Player human = new Player(playerName, colourA); 
        Player cpu1 = new CPU("CPU 1" , colourB , board); 
        Player cpu2 = new CPU("CPU 2" , colourC , board); 
        Player cpu3 = new CPU("CPU 3" , colourD , board); 

        human.setHand(Deck.drawCards());
        cpu1.setHand(Deck.drawCards());
        cpu2.setHand(Deck.drawCards());
        cpu3.setHand(Deck.drawCards());

        players.add(human); 
        players.add(cpu1); 
        players.add(cpu2); 
        players.add(cpu3); 
        
        this.currentPlayerIndex = 0;
        this.turn = 0;
        this.firePit = new ArrayList<Card>();
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getFirePit() {
        return firePit;
    }

    public void selectCard(Card card) throws InvalidCardException{
        players.get(currentPlayerIndex).selectCard(card);
    }

    public void selectMarble(Marble marble) throws InvalidMarbleException{
        players.get(currentPlayerIndex).selectMarble(marble);
    }

    public void deselectAll(){
        players.get(currentPlayerIndex).deselectAll();
    }

    public void editSplitDistance(int splitDistance) throws SplitOutOfRangeException{
        if(splitDistance<1 || splitDistance>6)
            throw new SplitOutOfRangeException("The provided value is outside the appropriate 1-6 range.");
        else
            board.setSplitDistance(splitDistance);
    }

    public Colour checkWin(){
        for(int i=0 ; i<board.getSafeZones().size() ; i++){
            SafeZone s = board.getSafeZones().get(i);
            if(checkAllOccupied(s.getCells()))
                return s.getColour();
        }
        return null;
    }
    private boolean checkAllOccupied(ArrayList<Cell> arr){
        for(int i=0 ; i<arr.size() ; i++){
            if (arr.get(i).getMarble()==null)
                return false;
        }
        return true;
    }





}
