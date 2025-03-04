package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {
    // A class representing any Safe Zone available on the board.
    // attributes are read only
    private final Colour colour;
    private final ArrayList<Cell> cells;

    public SafeZone(Colour colour) {
        this.colour = colour;
        cells = new ArrayList<Cell>();
        Cell cellA = new Cell(CellType.SAFE);
        Cell cellB = new Cell(CellType.SAFE);
        Cell cellC = new Cell(CellType.SAFE);
        Cell cellD = new Cell(CellType.SAFE);
        cells.add(cellA);
        cells.add(cellB);
        cells.add(cellC);
        cells.add(cellD);
    }

    public Colour getColour() {
        return colour;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
}
