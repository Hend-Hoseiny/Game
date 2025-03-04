package model.player;

import model.Colour;

public class Marble {
    private final Colour colour; // An enum representing the colour of the marble, and associating it to a Player

    public Marble(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}
