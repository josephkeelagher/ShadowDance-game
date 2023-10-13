import bagel.*;

/**
 * Lane that contains notes in the game.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public abstract class Lane implements DrawableLane, Resettable{
    protected final static int HEIGHT = 384;
    protected final static int TARGET_HEIGHT = 657;
    protected final Image image;
    protected int numNotes = 0;
    protected final String type;
    protected Keys relevantKey;
    protected final int location;
    protected int currNote = 0;

    /**
     * determines the type of lane
     * @return String the type of lane.
     */
    public abstract String getType();

    /**
     * adds a note to lane's list of notes.
     * @param note object to be added
     */
    public abstract void addNote(Note note);

    /**
     * draws the lane on the screen along with active notes.
     */
    public abstract void draw();

    /**
     * determines whether the lane has finished producing notes.
     * @return boolean true if it has, false if not.
     */
    public abstract boolean isFinished();

    /**
     * resets the lane and its notes to be replayable.
     */
    public abstract void reset();

    /**
     * Constructor for a lane
     * @param type the type of lane.
     * @param location the x value of the lane's location.
     */
    public Lane(String type, int location) {
        this.type = type;
        this.location = location;
        image = new Image("res/lane" + type + ".png");
        switch (type) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }
}
