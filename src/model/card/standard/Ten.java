package model.card.standard;

import engine.board.BoardManager;
import engine.GameManager;

public class Ten extends Standard {
    public Ten(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 10, suit, boardManager, gameManager);
    }

}
