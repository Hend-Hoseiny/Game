package engine.board;

public class SafeZone {
 //A class representing any Safe Zone available on the board.
	// attributes are read only
	private Colour colour;
	private ArrayList[] cells;
	public SafeZone(Colour colour){
		this.colour = colour;
		cells = new ArrayList[4];
	}
	public Colour getColour() {
		return colour;
	}
	public ArrayList[] getCells() {
		return cells;
	}
	
}
