package model.player;

public class Marble {
  private Colour colour; // An enum representing the colour of the marble, and associating it to a Player

  public Marble(Colour colour) { // get only
    this.colour = colour;
  }

  public Colour getColour() {
    return colour;
  }

}
// DONE