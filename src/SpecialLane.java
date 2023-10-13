import bagel.*;
import java.util.ArrayList;

/**
 * Lane that contains Special Notes which apply effects
 * @author Joseph Keelagher
 */
public class SpecialLane extends Lane implements DrawableLane, Resettable{
    // Inspired/Used code from Sample Project 1
    private final String type = "Special";
    private final ArrayList<SpecialNote> notes = new ArrayList<SpecialNote>();

    /**
     * Constructor for SpecialLane, initializes a new object of this class
     * @param location the x-value of the lane's location.
     */
    public SpecialLane(int location) {
        super("Special", location);
    }

    /**
     * returns Lane type
     * @return String lane type.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Checks if lane has any notes left to appear.
     * @return boolean true if no notes left, false if there is/are.
     */
    @Override
    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!notes.get(i).isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a new note to the list of notes in this lane.
     * @param note The note being added
     */
    @Override
    public void addNote(Note note) {
        notes.add((SpecialNote) note);
        numNotes++;
    }

    /**
     * Draws the Lane and its notes on screen.
     */
    @Override
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < numNotes; i++) {
                notes.get(i).draw(location);
        }
    }

    /**
     * resets the notes in the lane to be replayable.
     */
    @Override
    public void reset() {
        for (int i = 0; i < numNotes; i++) {
            notes.get(i).reset();
            currNote = 0;
        }
    }

    /**
     * Draws the lane and moves the position of all the notes down by a factor of the game speed.
     * Checks if an effect has been activated.
     * @param input Keyboard
     * @param effectHandler Object that handles certain effect interactions.
     * @return String null or the effect that has been activated.
     */
    public String update(Input input, EffectHandler effectHandler) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).update();
        }
        if (currNote < numNotes) {
            String effect = notes.get(currNote).checkEffect(input, effectHandler, TARGET_HEIGHT, Keys.SPACE);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return effect;
            }
        }
        return null;
    }
}
