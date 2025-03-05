package engine.board;

import java.util.ArrayList;
import java.util.Random;

import engine.GameManager;
import model.Colour;

public class Board implements BoardManager{
    private final GameManager gameManager;
    private final ArrayList<Cell> track;
    private final ArrayList<SafeZone> safeZones;
    private int splitDistance;

    public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
        this.gameManager = gameManager;
        this.track = new ArrayList<Cell>();
        this.safeZones = new ArrayList<SafeZone>();
        this.splitDistance = 3;
        
        initializeTrack();
        initializeSafeZones(colourOrder);

        for(int i=0 ; i<8 ; i++)
            assignTrapCell();

    }

    private void initializeTrack() {
        for(int i=0 ; i<100 ; i++){
            if(i==0 || i==25 || i==50 || i==75)
                track.add(new Cell(CellType.BASE));
            else if(i==23 || i==48 || i==73 || i==98)
                    track.add(new Cell(CellType.ENTRY));
            else
                track.add(new Cell(CellType.NORMAL));
        }
    }

    public void assignTrapCell() {
        Random random = new Random();
        while(true){
            int index = random.nextInt(100);
            Cell currentCell = track.get(index);
            if(currentCell.getCellType()==CellType.NORMAL && !currentCell.isTrap()){
                currentCell.setTrap(true);
                break;
            }
        }    
    }

    private void initializeSafeZones(ArrayList<Colour> colourOrder) {
        for (Colour colour : colourOrder) {
            safeZones.add(new SafeZone(colour));
        }
    }

    public ArrayList<Cell> getTrack() {
        return track;
    }

    public ArrayList<SafeZone> getSafeZones() {
        return safeZones;
    }

    public int getSplitDistance() {
        return splitDistance;
    }

    public void setSplitDistance(int splitDistance) {
        this.splitDistance = splitDistance;
    }

}
