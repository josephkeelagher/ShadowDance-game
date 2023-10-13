import bagel.*;

/**
 * Note that requires a hold press of a key to hit properly.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public class HoldNote extends Note implements DrawableNote, Resettable, Updateable{
    private static final int HEIGHT_OFFSET = 82;
    private int y = 24;
    private boolean holdStarted = false;
    private final Image image;

    /**
     * Constructor for a hold note.
     * @param type the direction of the note
     * @param appearanceFrame the frame the note first appears.
     */
    public HoldNote(String type, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/holdNote" + type + ".png");
    }

    /**
     * Checks and determines the final scoring for a hold note hit or miss.
     * @param input Keyboard input
     * @param accuracy Object that handles hit timing.
     * @param targetHeight y value of target.
     * @param relevantKey Key matching the direction of the note.
     * @return int the score for the note hit.
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));
            // Starting a hold
            if (score == Accuracy.MISS_SCORE) {
                deactivate();
                return score;
            } else if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }

        } // Ending a hold
        else if (isActive() && holdStarted) {

            int score = accuracy.evaluateScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        }

        return 0;
    }

    /**
     * resets the note to be replayable.
     */
    public void reset() {
        active = false;
        completed = false;
        holdStarted = false;
        y = 24;
    }

    /**
     * determines if the note is active
     * @return true if active, false if not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * determines if the note is completed
     * @return true if completed, false if not.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * deactivates a note marking it as completed for this level play.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }

    /**
     * moves notes down in the lane by a factor of game speed.
     */
    @Override
    public void update() {
        if (active) {
            y += speed;
        }

        if (Level.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    /**
     * draws the notes on screen.
     * @param x the x position to be drawn at.
     */
    @Override
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }

    /**
     * marks the start of a hold press of a key.
     */
    public void startHold() {
        holdStarted = true;
    }

    /**
     * determines height of the bottom/start of the hold note.
     * @return int the height of the start of the note.
     */
    private int getBottomHeight() {
        return y + HEIGHT_OFFSET;
    }

    /**
     * determines the height of the top/end of the hold note.
     * @return int the height of the end of the note.
     */
    private int getTopHeight() {
        return y - HEIGHT_OFFSET;
    }
}
