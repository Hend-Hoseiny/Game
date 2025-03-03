package model.card.standard;

public class Ace extends Standard {
	//A subclass of Standard representing a card of rank 1
	public Ace(String name, String description, Suit suit, BoardManager boardManager, GameManager
			gameManager){
		super( name, description, 1 , suit,  boardManager, gameManager);
	}

}
