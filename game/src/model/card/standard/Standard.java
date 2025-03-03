package model.card.standard;

public class Standard extends Card{
	// A standard card has the following 8 subclasses: Ace, King, Queen, Jack, Four, Five,
	//Seven, Ten
	// attributes are read only
	
	private int rank;  //An int representing the rank (number) of a standard card.
	
    private Suit suit;  //An enum representing the suit of a standard card
	 
   public  Standard(String name, String description, int rank, Suit suit, BoardManager boardManager,
		   GameManager gameManager){
	   super(name,description, boardManager,gameManager);
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
