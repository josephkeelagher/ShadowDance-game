import bagel.*;
import java.util.ArrayList;

/**
 * Lane that doesn't contain Special Notes (excluding Bombs).
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public class NormalLane extends Lane implements DrawableLane, Resettable{
    private final ArrayList<NormalNote> notes = new ArrayList<NormalNote>();
    private final ArrayList<HoldNote> holdNotes = new ArrayList<HoldNote>();
    private final ArrayList<BombNote> bombNotes = new ArrayList<BombNote>();
    private final EffectHandler effectHandler = new EffectHandler();
    private boolean bombed = false;
    private int numBombNotes = 0;
    private int currBombNote = 0;
    private int numHoldNotes = 0;
    private int currHoldNote = 0;

    /**
     * Constructor for a Normal Lane
     * @param type the direction of the lane.
     * @param location the x value of the lane location
     */
    public NormalLane(String type, int location) {
        super(type, location);
    }

    /**
     * determines the direction of the lane.
     * @return String the type/direction of the lane.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * resets the lane and its notes to be replayable.
     */
    @Override
    public void reset() {
        for (int i = 0; i < numNotes; i++) {
            notes.get(i).reset();
            currNote = 0;
        }
        for (int i = 0; i < numHoldNotes; i++) {
            holdNotes.get(i).reset();
            currHoldNote = 0;
        }
        for (int k = 0; k < numBombNotes; k++) {
            bombNotes.get(k).reset();
            currBombNote = 0;
        }
    }

    /**
     * adds any non special note to the lane (excluding bombs)
     * @param note object to be added
     */
    @Override
    public void addNote(Note note) {
        if (note instanceof NormalNote) {
            notes.add((NormalNote) note);
            numNotes++;
        }
        if (note instanceof HoldNote) {
            holdNotes.add((HoldNote) note);
            numHoldNotes++;
        }
        if (note instanceof BombNote) {
            bombNotes.add((BombNote) note);
            numBombNotes++;
        }
    }

    /**
     * determines if the lane has finished producing notes.
     * @return boolean true if it has finished, false if not.
     */
    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!notes.get(i).isCompleted()) {
                return false;
            }
        }

        for (int j = 0; j < numHoldNotes; j++) {
            if (!holdNotes.get(j).isCompleted()) {
                return false;
            }
        }

        return true;
    }

    /**
     * draws the lane and its note on screen.
     */
    @Override
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).draw(location);
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes.get(j).draw(location);
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            bombNotes.get(k).draw(location);
        }
    }

    /**
     * draws the lane and its notes as well as checks for any note hits.
     * @param input Keyboard input
     * @param accuracy Object that handles hit timing.
     * @return int the score awarded of any valid note hits.
     */
    public int update(Input input, Accuracy accuracy) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).update();
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes.get(j).update();
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            bombNotes.get(k).update();
        }

        if (currBombNote < numBombNotes) {
            String hit = bombNotes.get(currBombNote).checkEffect(input, effectHandler, TARGET_HEIGHT, relevantKey);
            if (bombNotes.get(currBombNote).isCompleted()) {
                currBombNote++;
                if (hit != null) {
                    bombed = true;
                }
                return Accuracy.NOT_SCORED;
            }
        }

        if (currNote < numNotes) {
            int score = notes.get(currNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return score;
            }
        }

        if (currHoldNote < numHoldNotes) {
            int score = holdNotes.get(currHoldNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes.get(currHoldNote).isCompleted()) {
                currHoldNote++;
            }
            return score;
        }
        return Accuracy.NOT_SCORED;
    }

    /**
     * determines if the lane needs to be cleared because of a bomb hit.
     * @return boolean true if needs to be cleared, false if not.
     */
    public boolean isBombed() {
        return bombed;
    }

    /**
     * clear the bombed tag, lane no longer needs to be cleared.
     */
    public void removeBombed() {
        bombed = false;
    }

    /**
     * clears the lane of active notes due to a bomb being hit.
     */
    public void clearLane() {
        for (int i = currNote; i < numNotes; i++) {
            if (notes.get(i).isActive()) {
                notes.get(i).deactivate();
            }
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            if (holdNotes.get(j).isActive()) {
                holdNotes.get(j).deactivate();
            }
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            if (bombNotes.get(k).isActive()) {
                bombNotes.get(k).deactivate();
            }
        }
    }


}
