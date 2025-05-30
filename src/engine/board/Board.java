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
import model.card.Card;
import model.player.Marble;

public class Board implements BoardManager {
    private final GameManager gameManager;
    private final ArrayList<Cell> track;
    private final ArrayList<SafeZone> safeZones;
    private int splitDistance;
    
    public ArrayList<Integer> trackPathIndices = new ArrayList<Integer>();
    public ArrayList<Integer> safePathIndices = new ArrayList<Integer>();
    public int trackSteps = 0;
    public int safeSteps = 0;

    public ArrayList<Cell> myFullPath = new ArrayList<Cell>();
    public ArrayList<Cell> myFullPathSplit = new ArrayList<Cell>();
    public int[] swapIndices = new int[2];
    public ArrayList<Integer> destroyIndices = new ArrayList<Integer>();
    public ArrayList<Colour> destroyColours = new ArrayList<Colour>();
    public boolean isFielding = false;
    public boolean isTrap = false;
    public int savingIndex = -1;

    public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
        this.gameManager = gameManager;
        this.track = new ArrayList<Cell>();
        this.safeZones = new ArrayList<SafeZone>();
        this.splitDistance = 3;

        initializeTrack();
        initializeSafeZones(colourOrder);

        for (int i = 0; i < 8; i++)
            assignTrapCell();

    }

    private void initializeTrack() {
        for (int i = 0; i < 100; i++) {
            if (i == 0 || i == 25 || i == 50 || i == 75)
                track.add(new Cell(CellType.BASE));
            else if (i == 23 || i == 48 || i == 73 || i == 98)
                track.add(new Cell(CellType.ENTRY));
            else
                track.add(new Cell(CellType.NORMAL));
        }
    }

    private void assignTrapCell() {
        Random random = new Random();
        while (true) {
            int index = random.nextInt(100);
            Cell currentCell = track.get(index);
            if (currentCell.getCellType() == CellType.NORMAL && !currentCell.isTrap()) {
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

    // -------------------------------------------------------------------------------------------------------------------------------------------------

    private ArrayList<Cell> getSafeZone(Colour colour) {
        for (SafeZone safeZone : this.safeZones) {
            if (safeZone.getColour() == colour)
                return safeZone.getCells();
        }
        return null;
    }

    private int getPositionInPath(ArrayList<Cell> path, Marble marble) {

        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getMarble() == marble)
                return i;
        }

        return -1;
    }

    private int getBasePosition(Colour colour) {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (this.safeZones.get(i).getColour() == colour)
                return index;
            index += 25;
        }
        return -1;
    }

    private int getEntryPosition(Colour colour) {
        int index = -2;

        if (this.safeZones.get(0).getColour() == colour)
            return 98;

        for (int i = 1; i < 4; i++) {
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

                        if (i >= safeZone.size()) {
                            throw new IllegalMovementException("Oops! Card rank is too high!");
                        }
                        stepsList.add(safeZone.get(i));
                    }
                } else {
                    for (int i = 1; i <= steps; i++)
                        stepsList.add(this.track.get((positionOnTrack + i) % 100));

                }
            } else {
                for (int i = 1; i <= Math.abs(steps); i++)
                    stepsList.add(this.track.get((positionOnTrack - i + 100) % 100));

            }

        } else {
            int indexInSafeZone = this.getPositionInPath(this.getSafeZone(color), marble);
            ArrayList<Cell> safeZone = this.getSafeZone(color);
            stepsList.add(safeZone.get(indexInSafeZone));

            if (steps >= 0) {
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

    private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy)
            throws IllegalMovementException {
        Colour activeColour = gameManager.getActivePlayerColour();
        int marbleBlockCount = 0;

        for (int i = 1; i < fullPath.size(); i++) {
            Cell cell = fullPath.get(i);
            Marble pathMarble = cell.getMarble();
            boolean isTarget = (i == fullPath.size() - 1);
            CellType cellType = cell.getCellType();

            // Rule 5: Safe Zone protection (no card can bypass or land on marbles in Safe
            // Zone)
            if (cellType == CellType.SAFE && pathMarble != null) {
                if (pathMarble.getColour() == activeColour)
                    throw new IllegalMovementException("Oops! Cannot bypass or destroy your own marble in safe zone!");
                throw new IllegalMovementException("Oops! Cannot bypass or land on a marble in its Safe Zone!");
            }

            if (pathMarble != null) {
                Colour marbleColour = pathMarble.getColour();

                // Rule 1: Cannot bypass or destroy your own marble
                if (marbleColour == activeColour && !destroy) {
                    throw new IllegalMovementException("Oops! Cannot bypass or destroy your own marble!");
                }

                // Rule 2: Cannot bypass or destroy a marble in its Base Cell
                if (cellType == CellType.BASE && getBasePosition(marbleColour) == track.indexOf(cell)) {
                    throw new IllegalMovementException("Oops! Cannot bypass or destroy a marble in its Base Cell!");
                }

                // Rule 3: Cannot bypass or destroy many marbles (only applies to intermediate
                // path, not the target)
                if (!isTarget && !destroy) {
                    marbleBlockCount++;
                    if (marbleBlockCount > 1) {
                        throw new IllegalMovementException("Oops! Cannot bypass more than one opponent marble!");
                    }
                }

                // Rule 4: Target cell has a marble
                if (isTarget && !destroy && pathMarble.getColour() == activeColour) {
                    throw new IllegalMovementException(
                            "Oops! Cannot land on a cell that is already occupied by your own marble!");
                }
            }

            // Entry → Safe Zone check (do not allow entering if another marble blocks the
            // entry)
            if (cellType == CellType.ENTRY && i + 1 < fullPath.size()) {
                Cell next = fullPath.get(i + 1);
                boolean goingIntoSafe = next.getCellType() == CellType.SAFE;
                boolean entryOccupied = cell.getMarble() != null;

                if (goingIntoSafe && entryOccupied) {
                    throw new IllegalMovementException("Oops! Cannot enter the Safe Zone! Entry is blocked.");
                }

            }
        }
    }

    private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException {
        if (this.getPositionInPath(this.track, marble_1) == -1 || this.getPositionInPath(this.track, marble_2) == -1)
            throw new IllegalSwapException("Oops! Cannot swap marbles not on the track!");

        if (marble_1.getColour() == gameManager.getActivePlayerColour()
                && this.getPositionInPath(this.track, marble_2) == getBasePosition(marble_2.getColour()))
            throw new IllegalSwapException("Oops! Cannot swap a marble in its Base Cell!");

        if (marble_2.getColour() == gameManager.getActivePlayerColour()
                && this.getPositionInPath(this.track, marble_1) == getBasePosition(marble_1.getColour()))
            throw new IllegalSwapException("Oops! Cannot swap a marble in its Base Cell!");
    }

    private void validateDestroy(int positionInPath) throws IllegalDestroyException {

        // Case (a): Destroying a marble that isn't on the track
        if (positionInPath < 0 || positionInPath >= track.size()) {
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

    // mile stone 2
    // Method 10
    // Check if you can place a marble in the Base Cell
    private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException {
        Marble occupyingMarble = occupiedBaseCell.getMarble();
        if (occupyingMarble != null && occupyingMarble.getColour() == gameManager.getActivePlayerColour()) {
            throw new CannotFieldException("Cannot field marble: Base Cell is already occupied by a friendly marble.");
        }
    }

    // method 11
    private void validateSaving(int positionInSafeZone, int positionOnTrack) throws InvalidMarbleException {
        if (positionInSafeZone != -1) {
            throw new InvalidMarbleException("Marble is already in the Safe Zone.");
        }
        if (positionOnTrack == -1) {
            throw new InvalidMarbleException("Marble is not on the track and cannot be sent to Safe Zone.");
        }
    }

    // method 12
    private int getIndexInTrack(Cell c) {
        return track.indexOf(c);
    }

    private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalDestroyException {
        // Remove marble from starting position
        track.get(getIndexInTrack(fullPath.get(0))).setMarble(null);

        // Handle path destruction (King card only)
        for (int i = 1; i < fullPath.size() - 1; i++) {
            Cell pathCell = fullPath.get(i);
            if (destroy && pathCell.getCellType() != CellType.SAFE) {
                if (pathCell.getMarble() != null) {
                    destroyMarble(pathCell.getMarble());
                }
            }
        }

        // Handle target cell (modified validation)
        Cell targetCell = fullPath.get(fullPath.size() - 1);

        // Place marble in target
        if (targetCell.getMarble() != null)
            // gameManager.sendHome(targetCell.getMarble());
            destroyMarble(targetCell.getMarble());
        targetCell.setMarble(marble);

        // Handle trap cell
        if (targetCell.isTrap()) {
            isTrap = true;
            destroyMarble(marble);
            targetCell.setTrap(false);
            assignTrapCell();
        }
    }

    public void moveBy(Marble marble, int steps, boolean destroy)
            throws IllegalMovementException, IllegalDestroyException {
        ArrayList<Cell> fullPath = validateSteps(marble, steps);
        validatePath(marble, fullPath, destroy);
        move(marble, fullPath, destroy);
                
        safeSteps = 0;
        trackSteps = 0;
        for (int i = 1; i < fullPath.size(); i++) {
            Cell pathCell = fullPath.get(i);
            isTrap = false;
            if (pathCell.getCellType() == CellType.SAFE) {
                safePathIndices.add(getSafeZone(gameManager.getActivePlayerColour()).indexOf(pathCell));
                safeSteps++;
            } else {
                trackPathIndices.add(track.indexOf(pathCell));
                trackSteps++;
            }
        }
        
        if(myFullPath.size()>0)
            myFullPathSplit=fullPath;
        else
            myFullPath=fullPath;
        // if (trackSteps == 0)
        //     safeSteps--;
        // else
        //     trackSteps--;
    }

    // method 13

    public void swap(Marble marble1, Marble marble2) throws IllegalSwapException {
        validateSwap(marble1, marble2);

        // Find cells
        Cell cell1 = track.get(this.getPositionInPath(track, marble1));
        Cell cell2 = track.get(this.getPositionInPath(track, marble2));

        swapIndices[0] = track.indexOf(cell1);
        swapIndices[1] = track.indexOf(cell2);

        // Swap
        cell1.setMarble(marble2);
        cell2.setMarble(marble1);

    }

    public void destroyMarble(Marble marble) throws IllegalDestroyException {

        // First check track
        int positionInPath = getPositionInPath(track, marble);
        int positionInSafeZone = getPositionInPath(getSafeZone(marble.getColour()), marble);
        boolean isInHome = positionInPath == -1 && positionInSafeZone == -1;

        if (marble.getColour() != gameManager.getActivePlayerColour())
            validateDestroy(positionInPath);

        if (positionInSafeZone != -1)
            throw new IllegalDestroyException("Cannot burn marble in its safe zone");

        // Remove marble from its cell if found
        if (positionInPath != -1) {
            Cell currentCell = track.get(positionInPath);
            destroyIndices.add(getTrack().indexOf(currentCell));
            destroyColours.add(currentCell.getMarble().getColour());
            currentCell.setMarble(null);
            gameManager.sendHome(marble);
        }

        if (isInHome)
            throw new IllegalDestroyException("Cannot burn marble in home zone");

    }
    // method 15

    public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
        int basePos = getBasePosition(marble.getColour());
        Cell baseCell = track.get(basePos);

        if (baseCell.getMarble() != null) {
            validateFielding(baseCell);
            destroyMarble(baseCell.getMarble());
        }
        baseCell.setMarble(marble);
        isFielding = true;
    }

    // method 16

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
        savingIndex = getSafeZone(gameManager.getActivePlayerColour()).indexOf(emptySafeZones.get(0));

    }

    // method 17

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
