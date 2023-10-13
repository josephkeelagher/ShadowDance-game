import bagel.*;

/**
 * abstract class for any note that appears in the game.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public abstract class Note implements DrawableNote, Resettable, Updateable{
    protected final int appearanceFrame;
    protected static int speed = 2;
    protected boolean active = false;
    protected boolean completed = false;

    /**
     * constructor for a note in the level.
     * @param appearanceFrame int the frame in which note first appears
     */
    public Note(int appearanceFrame) {
        this.appearanceFrame = appearanceFrame;
    }

    /**
     * resets the note to be replayable.
     */
    public abstract void reset();

    /**
     * determines if the note is to be rendered on screen.
     * @return boolean true if to be rendered, false if not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * determines if the note has been hit or missed.
     * @return boolean true if hit or missed, false if not.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * sets the note not to be rendered anymore and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }

    /**
     * doubles the movement speed of note
     */
    public static void doubleSpeed() {
        speed = 4;
    }

    /**
     * sets the movement speed of the note to its original value.
     */
    public static void normalSpeed() {
        speed = 2;
    }

    /**
     * moves note position down in the lane by a factor of game speed.
     */
    public abstract void update();

    /**
     * draws the note in the lane.
     * @param x int x value to be drawn at.
     */
    public abstract void draw(int x);
}
