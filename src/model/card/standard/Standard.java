package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.Card;

public class Standard extends Card {
    // A standard card has the following 8 subclasses: Ace, King, Queen, Jack, Four,
    // Five,
    // Seven, Ten
    // attributes are read only

    private final int rank; // An int representing the rank (number) of a standard card.

    private final Suit suit; // An enum representing the suit of a standard card

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

}
