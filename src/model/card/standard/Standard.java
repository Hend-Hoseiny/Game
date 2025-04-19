package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.card.Card;
import model.player.Marble;

public class Standard extends Card {
    // A standard card has the following 8 subclasses: Ace, King, Queen, Jack, Four,
    // Five,
    // Seven, Ten
    // attributes are read only

    private final int rank; 

    private final Suit suit; 

    public Standard(String name, String description, int rank, Suit suit, BoardManager boardManager,
            GameManager gameManager) {
        super(name, description, boardManager, gameManager);
        this.rank = rank;
        this.suit = suit;

    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
    
    public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
        if (!validateMarbleSize(marbles)) {
            throw new InvalidMarbleException("Seven requires 1 or 2 marbles");
        }
        if (!validateMarbleColours(marbles)) {
            throw new InvalidMarbleException("Seven can only move your own marbles");
        }
                boardManager.moveBy(marbles.get(0), rank , false);
         
           
        }
    }



