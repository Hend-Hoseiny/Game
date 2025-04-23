package engine.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import engine.GameManager;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;
import model.Colour;
import model.player.Marble;

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

    private void assignTrapCell() {
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




//-------------------------------------------------------------------------------------------------------------------------------------------------

    
private ArrayList<Cell> getSafeZone(Colour colour) {
    for (SafeZone safeZone : this.safeZones) {
        if (safeZone.getColour() == colour)
            return safeZone.getCells();
    }
    return null;
}

private int getPositionInPath(ArrayList<Cell> path, Marble marble){
    
    for (int i = 0; i < path.size(); i++) {
        if (path.get(i).getMarble() == marble)
            return i;
    }
    
    return -1;
} 

private int getBasePosition(Colour colour){
    int index=0;
    for(int i=0; i<4; i++){
        if(this.safeZones.get(i).getColour() == colour)
            return index;
        index += 25;
    }
    return -1;
}


private int getEntryPosition(Colour colour){
    int index= -2;

    if(this.safeZones.get(0).getColour() == colour)
        return 98;

    for(int i=1; i<4; i++){
        index += 25;
        if (this.safeZones.get(i).getColour() == colour)
            return index;
    }
    return -1;
}




private ArrayList<Cell> validateSteps(Marble marble, int steps) throws IllegalMovementException {
    int positionOnTrack = this.getPositionInPath(this.track, marble);
    ArrayList<Cell> stepsList = new ArrayList<>();
    Colour color = marble.getColour();
    Colour activeColour = gameManager.getActivePlayerColour();

    if (positionOnTrack == -1 && this.getPositionInPath(this.getSafeZone(color), marble) == -1) 
        throw new IllegalMovementException("Oops! Marble cannot move.");
    

    if (positionOnTrack != -1) {
        stepsList.add(this.track.get(positionOnTrack));
        int entryPosition = this.getEntryPosition(gameManager.getActivePlayerColour());
        int distanceToEntry = (entryPosition - positionOnTrack + 100) % 100;

        if (steps > 0) {
            if (steps > distanceToEntry && marble.getColour() == gameManager.getActivePlayerColour()) {
                for (int i = 1; i <= distanceToEntry; i++) 
                    stepsList.add(this.track.get((positionOnTrack + i) % 100));
                
                int remainingSteps = steps - distanceToEntry;
                ArrayList<Cell> safeZone = this.getSafeZone(color);

                for (int i = 0; i < remainingSteps; i++) {
                    // if(activeColour != color) {
                    //     throw new IllegalMovementException("Oops! Cannot move a marble in another player's Safe Zone.");
                    // }
                    if (i >= safeZone.size() ) {
                        throw new IllegalMovementException("Oops! Card rank is too high!");
                    }
                    stepsList.add(safeZone.get(i));
                }
            } 
            else {
                    for (int i = 1; i <= steps; i++) 
                        stepsList.add(this.track.get((positionOnTrack + i) % 100));
                
            }
        } 
        else {
                for (int i = 1; i <= Math.abs(steps); i++) 
                    stepsList.add(this.track.get((positionOnTrack - i + 100) % 100));

        }

    } 
    else {
            int indexInSafeZone = this.getPositionInPath(this.getSafeZone(color), marble);
            ArrayList<Cell> safeZone = this.getSafeZone(color);
            stepsList.add(safeZone.get(indexInSafeZone));

            // if(activeColour != color) {
            //     throw new IllegalMovementException("Oops! Cannot move a marble in another player's Safe Zone.");
            // }

            if (steps > 0) {
                if (steps > safeZone.size() - indexInSafeZone - 1) {
                    throw new IllegalMovementException("Oops! Card rank is too high!");
                }
                for (int i = 1; i <= steps; i++) {
                    stepsList.add(safeZone.get(indexInSafeZone + i));
                }
            } else {
                throw new IllegalMovementException("Oops! Cannot move backward in the safe zone.");
            }
    }

    return stepsList;
}



//validate against illegal moves specified
private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalMovementException {
    int count = 0;
    int size1 = fullPath.size() - 1;
    Colour activeColour = gameManager.getActivePlayerColour();

    // ASK ABOUT THIS!!!!!!!!!!!!!!
    for (int i = 0; i < fullPath.size() && fullPath.size()>1; i++) {
        Marble pathMarble = fullPath.get(i).getMarble();

        // Skip if the cell is empty
        if (pathMarble == null) {
            continue;
        }

        Colour marbleColor = pathMarble.getColour();

        // Rule 1: Cannot bypass or destroy your own marble -  Self-Blocking: A marble cannot move if there is another marble owned by the same
        // player either in its path or at the target position.
        if (activeColour == marbleColor && !destroy) {
            throw new IllegalMovementException("Oops! Cannot bypass or destroy your own marble!");
        }

        // Rule 2: Cannot bypass or destroy a marble in its Base Cell
        if (fullPath.get(i).getCellType() == CellType.BASE && getBasePosition(marbleColor) == track.indexOf(fullPath.get(i))) {
            throw new IllegalMovementException("Oops! Cannot bypass or destroy a marble in its Base Cell!");
        }

        // Rule 3: Cannot bypass or destroy many marbles (applies to the path only, excluding the target) - Path Blockage: Movement is invalid if there is more than one marble (owned by
        // any player) blocking the path.
        if (activeColour != marbleColor) {
            count++;
            if (!destroy && count > 1 && i < size1) {
                throw new IllegalMovementException("Oops! Cannot bypass or destroy many marbles!");
            }
        }

        // Rule 4: Cannot enter the Safe Zone of another marble (applies to both path and target) -  Safe Zone Entry: A marble cannot enter its player’s Safe Zone if any marble is
        //stationed at its player’s Safe Zone Entry.
        // if (!destroy && fullPath.get(i).getCellType() == CellType.ENTRY) {
        //     throw new IllegalMovementException("Oops! Cannot enter the Safe Zone!");
        // }
        if (fullPath.get(i).getCellType() == CellType.ENTRY && i + 1 < fullPath.size() && fullPath.get(i+1).getCellType() == CellType.SAFE){
            throw new IllegalMovementException("Oops! Cannot enter the Safe Zone!");
        }

        // Rule 5: Safe Zone protection (even a King cannot bypass or land on a Safe Zone marble)
        if (fullPath.get(i).getCellType() == CellType.SAFE) {
            throw new IllegalMovementException("Oops! Cannot bypass or land on a marble in its Safe Zone!");
        }
    }
}



private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException{        
    if(this.getPositionInPath(this.track, marble_1) == -1 ||this.getPositionInPath(this.track, marble_2) == -1)
        throw new IllegalSwapException("Oops! Cannot swap marbles not on the track!");
    
    if(this.getPositionInPath(this.track, marble_2) == getBasePosition(marble_2.getColour()))
        throw new IllegalSwapException("Oops! Cannot swap a marble in its Base Cell!");

    if(marble_1.getColour() == marble_2.getColour())
        throw new IllegalSwapException("Oops! Cannot swap your own marbles!");
        
}


private void validateDestroy(int positionInPath) throws IllegalDestroyException{
    if(this.track.get(positionInPath).getMarble() == null)
        throw new IllegalDestroyException("Oops! Cannot destroy a marble that is not on the track!");
    
    if(this.track.get(positionInPath).getCellType() == CellType.BASE)
        throw new IllegalDestroyException("Oops! Cannot destroy a marble in its Base Cell!");
    
    if(this.track.get(positionInPath).getCellType() == CellType.SAFE)
        throw new IllegalDestroyException("Oops! Cannot destroy a marble in its Safe Zone!");
}

//hageeb el color ezay??????
private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException{
    if(occupiedBaseCell.getMarble() != null)
        throw new CannotFieldException("Oops! Cannot field a marble in a Base Cell that is not empty!");

    }

    
private void validateSaving(int positionInSafeZone, int positionOnTrack) throws InvalidMarbleException{
    if(positionOnTrack == -1)
        throw new InvalidMarbleException("Oops! Cannot save a marble in the Home Zone");
    
    if(this.track.get(positionOnTrack).getCellType() == CellType.SAFE)
        throw new InvalidMarbleException("Oops! Cannot save a marble in its Safe Zone!");
}


//method 12
private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalDestroyException{

}
public void moveBy(Marble marble, int steps, boolean destroy) throws IllegalMovementException, IllegalDestroyException {
ArrayList<Cell> fullPath = validateSteps(marble, steps);
validatePath(marble, fullPath, destroy);
move(marble, fullPath, destroy);
}

//method 13


public void swap(Marble marble1, Marble marble2) throws IllegalSwapException {
validateSwap(marble1, marble2);

// Find cells
Cell cell1 = track.get(this.getPositionInPath(track, marble1));
Cell cell2 = track.get(this.getPositionInPath(track, marble2));

// Swap
if (cell1 != null && cell2 != null) {
    cell1.setMarble(marble2);
    cell2.setMarble(marble1);
}
}


//method 14

public void destroyMarble(Marble marble) throws IllegalDestroyException {
Cell currentCell = track.get(this.getPositionInPath(track, marble));
if (currentCell == null) {
    throw new IllegalDestroyException("Marble not on track, cannot destroy.");
}
validateDestroy(getPositionInPath(track, marble));
currentCell.setMarble(null);
gameManager.sendHome(marble);
}

//method 15

public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
int basePos = getBasePosition(marble.getColour());
Cell baseCell = track.get(basePos);

if (baseCell.getMarble() != null) {
    validateFielding(baseCell);
    destroyMarble(baseCell.getMarble());
}

baseCell.setMarble(marble);
}

//method 16

public void sendToSafe(Marble marble) throws InvalidMarbleException {
ArrayList<Cell> zone = getSafeZone(marble.getColour());
int inSafe = getPositionInPath(zone, marble);
int onTrack = getPositionInPath(track, marble);

validateSaving(inSafe, onTrack);

// Remove from current track cell
Cell current = track.get(this.getPositionInPath(track, marble));
if (current != null) {
    current.setMarble(null);
}

// Assign to first available cell in safe zone
for (Cell c : zone) {
    if (c.getMarble() == null) {
        c.setMarble(marble);
        return;
    }
}
}

//method 17

public ArrayList<Marble> getActionableMarbles() {
ArrayList<Marble> actionable = new ArrayList<>();
Colour active = gameManager.getActivePlayerColour();

// From track
for (Cell c : track) {
    if (c.getMarble() != null && c.getMarble().getColour() == active) {
        actionable.add(c.getMarble());
    }
}

// From safe zone
ArrayList<Cell> zone = getSafeZone(active);
for (Cell c : zone) {
    if (c.getMarble() != null && c.getMarble().getColour() == active) {
        actionable.add(c.getMarble());
    }
}

return actionable;
}


}














