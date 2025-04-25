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
    
    
    
    private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalMovementException {
        Colour activeColour = gameManager.getActivePlayerColour();
        int marbleBlockCount = 0;
    
        for (int i = 1; i < fullPath.size(); i++) {
            Cell cell = fullPath.get(i);
            Marble pathMarble = cell.getMarble();
            boolean isTarget = (i == fullPath.size() - 1);
            CellType cellType = cell.getCellType();
    
            // Rule 5: Safe Zone protection (no card can bypass or land on marbles in Safe Zone)
            if (cellType == CellType.SAFE && pathMarble != null) {
                throw new IllegalMovementException("Oops! Cannot bypass or land on a marble in its Safe Zone!");
            }
    
            if (pathMarble != null) {
                Colour marbleColour = pathMarble.getColour();
    
                // Rule 1: Cannot bypass or destroy your own marble
                if (marbleColour == activeColour) {
                    throw new IllegalMovementException("Oops! Cannot bypass or destroy your own marble!");
                }
    
                // Rule 2: Cannot bypass or destroy a marble in its Base Cell
                if (cellType == CellType.BASE && getBasePosition(marbleColour) == track.indexOf(cell)) {
                    throw new IllegalMovementException("Oops! Cannot bypass or destroy a marble in its Base Cell!");
                }
    
                // Rule 3: Cannot bypass or destroy many marbles (only applies to intermediate path, not the target)
                if (!isTarget && !destroy) {
                    marbleBlockCount++;
                    if (marbleBlockCount > 1) {
                        throw new IllegalMovementException("Oops! Cannot bypass more than one opponent marble!");
                    }
                }
    
                // Rule 4: Target cell has a marble
                if (isTarget && !destroy) {
                    throw new IllegalMovementException("Oops! Cannot land on a cell that is already occupied!");
                }
            }
    
            // Entry → Safe Zone check (do not allow entering if another marble blocks the entry)
            if (cellType == CellType.ENTRY && i + 1 < fullPath.size()) {
                Cell next = fullPath.get(i + 1);
                if (next.getCellType() == CellType.SAFE && next.getMarble() != null) {
                    throw new IllegalMovementException("Oops! Cannot enter the Safe Zone! Entry is blocked.");
                }
            }
        }
    }
    
    
    

    private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException{        
        if(this.getPositionInPath(this.track, marble_1) == -1 ||this.getPositionInPath(this.track, marble_2) == -1)
            throw new IllegalSwapException("Oops! Cannot swap marbles not on the track!");
        
        if(marble_1.getColour()==gameManager.getActivePlayerColour()&&this.getPositionInPath(this.track, marble_2) == getBasePosition(marble_2.getColour()))
            throw new IllegalSwapException("Oops! Cannot swap a marble in its Base Cell!");

        if(marble_2.getColour()==gameManager.getActivePlayerColour()&&this.getPositionInPath(this.track, marble_1) == getBasePosition(marble_1.getColour()))
            throw new IllegalSwapException("Oops! Cannot swap a marble in its Base Cell!");   
    }


    private void validateDestroy(int positionInPath) throws IllegalDestroyException {
        // Case (a): Destroying a marble that isn't on the track
        if (positionInPath == -1 || positionInPath >= track.size()) {
            throw new IllegalDestroyException("Oops! Cannot destroy a marble that is not on the track.");
        }
    
        Cell c = track.get(positionInPath);
        if (c.getCellType() == CellType.BASE &&
            c.getMarble() != null &&
            getBasePosition(c.getMarble().getColour()) == positionInPath)
            throw new IllegalDestroyException("Oops! Cannot destroy a marble in its own Base Cell!");
        if (c.getCellType() == CellType.SAFE)
            throw new IllegalDestroyException("Oops! Cannot destroy a marble in its Safe Zone!");
    }
    

    
     //mile stone 2
    // Method 10 
    // Check if you can place a marble in the Base Cell
    private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException {
        Marble occupyingMarble = occupiedBaseCell.getMarble();
        if (occupyingMarble != null && occupyingMarble.getColour()==gameManager.getActivePlayerColour()) {
            throw new CannotFieldException("Cannot field marble: Base Cell is already occupied by a friendly marble.");
        }
    }
    
    //method 11
    private void validateSaving(int positionInSafeZone, int positionOnTrack) throws InvalidMarbleException {
        if (positionInSafeZone != -1) {
            throw new InvalidMarbleException("Marble is already in the Safe Zone.");
        }
        if (positionOnTrack == -1) {
            throw new InvalidMarbleException("Marble is not on the track and cannot be sent to Safe Zone.");
        }
    }
    
    //method 12
    private int getIndexInTrack (Cell c){
        return track.indexOf(c);
    }
    private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalDestroyException{
        track.get(getIndexInTrack(fullPath.get(0))).setMarble(null);
        for(int i=1 ; i<fullPath.size()-1 ; i++){
            if(destroy && fullPath.get(i).getCellType()!=CellType.SAFE)
              track.get(getIndexInTrack(fullPath.get(i))).setMarble(null);
        }
        int targetIndex = getIndexInTrack(fullPath.get(fullPath.size()-1));
        track.get(targetIndex).setMarble(marble);
        if(track.get(targetIndex).isTrap()){
            destroyMarble(marble);
            track.get(targetIndex).setTrap(false);
            assignTrapCell();
        }

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
        cell1.setMarble(marble2);
        cell2.setMarble(marble1);
        
    }

    
  // Method 14: destroyMarble
public void destroyMarble(Marble marble) throws IllegalDestroyException {
    // Get the marble's position in the track
    int positionInPath = getPositionInPath(track, marble);

    // Validate that this marble is eligible for destruction
    validateDestroy(positionInPath);

    // Defensive check: ensure index is valid before accessing
    if (positionInPath >= 0 && positionInPath < track.size()) {
        Cell currentCell = track.get(positionInPath);

        // Remove the marble from the track cell
        if (currentCell != null && currentCell.getMarble() == marble) {
            currentCell.setMarble(null);
        }
    }

    // Send marble back to player's home (regain)
    gameManager.sendHome(marble);

    // Optional: System.out.println("Marble destroyed and returned home: " + marble);
}

    
    //method 15
    
    public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
        int basePos = getBasePosition(marble.getColour());
        Cell baseCell = track.get(basePos);

        if (baseCell.getMarble() != null) {
            validateFielding(baseCell);
            // destroyMarble(baseCell.getMarble());
            // gameManager.sendHome(baseCell.getMarble());
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
        ArrayList<Cell> emptySafeZones = new ArrayList<Cell>();
        for (Cell c : zone) {
            if (c.getMarble() == null) {
                emptySafeZones.add(c);
            }
        }
        Collections.shuffle(emptySafeZones);
        emptySafeZones.get(0).setMarble(marble);

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














