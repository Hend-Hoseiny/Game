package engine;

import engine.board.Board;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
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
}
