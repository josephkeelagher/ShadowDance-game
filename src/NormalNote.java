import bagel.*;

/**
 * Normal type note, requiring just a press off a button to hit and provides only score.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public class NormalNote extends Note implements DrawableNote, Resettable, Updateable{
    private int y = 100;
    private final Image image;

    /**
     * Constructor for a normal note.
     * @param type the direction of the note
     * @param appearanceFrame the frame in which it first appears.
     */
    public NormalNote(String type, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/note" + type + ".png");
    }

    /**
     * Checks and determines the final scoring for a normal note hit or miss.
     * @param input Keyboard input
     * @param accuracy Object that handles hit timing.
     * @param targetHeight y value of target.
     * @param relevantKey Key matching the direction of the note.
     * @return int the score for the note hit.
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int score = accuracy.evaluateScore(y, targetHeight, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }

    /**
     * resets the note to be replayable.
     */
    @Override
    public void reset() {
        active = false;
        completed = false;
        y = 100;
    }

    /**
     * determines whether the note is to be rendered on screen.
     * @return boolean true if needs to rendered, false if not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * determines if the note has either been hit or missed.
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
     * moves the note down by a factor of the game speed.
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
     * draws the note on screen when it appears.
     * @param x the x value of the note location.
     */
    @Override
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }


}
