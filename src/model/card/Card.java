package model.card;

import engine.GameManager;
import engine.board.BoardManager;

public abstract class Card {
    // A class representing Cards in the game.
    private final String name;
    private final String description;
    protected BoardManager boardManager;
    protected GameManager gameManager;

    public Card(String name, String description, BoardManager boardManager, GameManager gameManager) {
        this.name = name;
        this.description = description;
        this.gameManager = gameManager;
        this.boardManager = boardManager;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
