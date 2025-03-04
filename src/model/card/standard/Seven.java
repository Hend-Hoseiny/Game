package model.card.standard;

import engine.board.BoardManager;
import engine.GameManager;

public class Seven extends Standard {
    public Seven(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 7, suit, boardManager, gameManager);
    }

}