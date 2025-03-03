package model.card.wild;

abstract class Wild extends Card  {
 // A class representing the wild cards available in the game.
//A wild card has the following 2 subclasses: Burner, Saver
	public Wild(String name, String description, BoardManager boardManager, GameManager gameManager){
		super(name,description, boardManager,gameManager);
	}
}
