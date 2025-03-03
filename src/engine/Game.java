package engine;

import engine.board.Board;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.Player;

import java.io.*;
import java.util.*;

public class Game implements GameManager {

    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Card> firePit;
    @SuppressWarnings("unused")
    private int currentPlayerIndex;
    @SuppressWarnings("unused")
    private int turn;

    private static final ArrayList<Colour> generateColourOrder(){
        ArrayList<Colour> colourOrder = new ArrayList<>();
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

    public Game(String playerName) throws IOException{
        ArrayList<Colour> colourOrder = generateColourOrder();
        this.board=new Board(colourOrder,this);
        Deck.loadCardPool(board, this);
        players = new ArrayList<Player>();
        players.add(new Player()); //pass paremeters and add CPU
        //ArrayList<Card> a = Deck.drawCards() , setHand for each player
        this.currentPlayerIndex=0;
        this.turn=0;
        this.firePit=new ArrayList<Card>();
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Card> getFirePit() {
        return this.firePit;
    }
}
