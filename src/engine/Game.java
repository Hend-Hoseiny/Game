package engine;

import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import engine.board.*;
import exception.GameException;
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

    public int discardedIndex;
    public Card dCard;
    public boolean isRefill=false;

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
        Player cpu1 = new CPU("CPU 1", colourB, board);
        Player cpu2 = new CPU("CPU 2", colourC, board);
        Player cpu3 = new CPU("CPU 3", colourD, board);

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

    // -------------------------------------------------------------------------------------------------------------------------------------------

    public void selectCard(Card card) throws InvalidCardException {
        players.get(currentPlayerIndex).selectCard(card);
    }

    public void selectMarble(Marble marble) throws InvalidMarbleException {
        players.get(currentPlayerIndex).selectMarble(marble);
    }

    public void deselectAll() {
        players.get(currentPlayerIndex).deselectAll();
    }

    public void editSplitDistance(int splitDistance) throws SplitOutOfRangeException {
        if (splitDistance < 1 || splitDistance > 6)
            throw new SplitOutOfRangeException("The provided value is outside the appropriate 1-6 range.");
        else
            board.setSplitDistance(splitDistance);
    }

    public boolean canPlayTurn() {
        ArrayList<Card> currentHand = players.get(currentPlayerIndex).getHand();
        return currentHand.size() + turn == 4;
    }

    public void playPlayerTurn() throws GameException {
        players.get(currentPlayerIndex).play();
    }

    public void endPlayerTurn() {
        board.trackPathIndices.clear();
        board.safePathIndices.clear();
        board.trackSteps = 0;
        board.safeSteps = 0;
        board.destroyIndices.clear();
        board.destroyColours.clear();
        board.swapIndices[0] = -1;
        board.isFielding = false;
        board.isTrap = false;
        board.savingIndex = -1;
        discardedIndex=-1;
        dCard=null;
        board.myFullPath.clear();
        board.myFullPathSplit.clear();

        Player currentPlayer = players.get(currentPlayerIndex);
        Card selectedCard = currentPlayer.getSelectedCard();
        currentPlayer.getHand().remove(selectedCard);
        firePit.add(selectedCard);
        currentPlayer.deselectAll();
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        if (currentPlayerIndex == 0)
            turn = (turn + 1) % 4;
        if (turn == 0)
            for (Player p : players) {
                if (Deck.getPoolSize() < 4) {
                    isRefill=true;
                    Deck.refillPool(firePit);
                    firePit.clear();
                }
                p.setHand(Deck.drawCards());
            }
    }

    public Colour checkWin() {
        for (int i = 0; i < board.getSafeZones().size(); i++) {
            SafeZone s = board.getSafeZones().get(i);
            if (s.isFull())
                return s.getColour();
        }
        return null;
    }

    public void sendHome(Marble marble) {
        for (Player player : players) {
            if (player.getColour() == marble.getColour())
                player.regainMarble(marble);
        }
    }

    public void fieldMarble() throws CannotFieldException, IllegalDestroyException {
        Player currentPlayer = players.get(currentPlayerIndex);
        ArrayList<Marble> currentHome = currentPlayer.getMarbles();
        if (currentHome.isEmpty())
            throw new CannotFieldException("Player has no marbles in home zone");
        Marble currentMarble = currentHome.get(0);
        board.sendToBase(currentMarble);
        currentHome.remove(currentMarble);
    }

    public void discardCard(Colour colour) throws CannotDiscardException{
        for(Player p:players){
            if(p.getColour()==colour){
                ArrayList<Card> currentHand = new ArrayList<Card>();
                currentHand.addAll(p.getHand());
                if(currentHand.size()==0)
                    throw new CannotDiscardException("Player has no cards in hand to be discarded");
                else{
                    Collections.shuffle(currentHand);
                    Card discardedCard = currentHand.get(0);
                    firePit.add(discardedCard);
                    p.getHand().remove(discardedCard);
                    dCard=discardedCard;

                }
            }  
        }
    }

    public void discardCard() throws CannotDiscardException{
        Random r = new Random();
        int index=r.nextInt(4);
        while(index==currentPlayerIndex)
            index=r.nextInt(4);
        Colour currentColour = players.get(index).getColour();
        discardCard(currentColour);
        discardedIndex = index;
    }

    public Colour getActivePlayerColour() {
        return players.get(currentPlayerIndex).getColour();
    }

    public Colour getNextPlayerColour() {
        int index = (currentPlayerIndex + 1) % 4;
        return players.get(index).getColour();
    }

}
