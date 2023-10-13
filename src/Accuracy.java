import bagel.Font;
import bagel.Window;

/**
 * Handles accuracy scoring for notes.
 * @author Joseph Keelagher
 * Inspired/Used from Project 1 Solution.
 */
public class Accuracy implements Updateable{
    /**
     * score awarded for a perfect hit,
     */
    public static final int PERFECT_SCORE = 10;
    /**
     * score awarded for a good hit.
     */
    public static final int GOOD_SCORE = 5;
    /**
     * score awarded for a bad hit.
     */
    public static final int BAD_SCORE = -1;
    /**
     * score awarded for a miss.
     */
    public static final int MISS_SCORE = -5;
    /**
     * score awarded for no hit.
     */
    public static final int NOT_SCORED = 0;
    /**
     * feedback message for a perfect hit.
     */
    public static final String PERFECT = "PERFECT";
    /**
     * feedback message for a good hit.
     */
    public static final String GOOD = "GOOD";
    /**
     * feedback message for a bad hit.
     */
    public static final String BAD = "BAD";
    /**
     * feedback message for a miss.
     */
    public static final String MISS = "MISS";
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currAccuracy = null;
    private int frameCount = 0;

    /**
     * sets the feedback message to be displayed.
     * @param accuracy the message being displayed.
     */
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    /**
     * decides on the score to be awarded for a specific hit.
     * @param height integer height of the note.
     * @param targetHeight integer height of the target.
     * @param triggered boolean for if a key is pressed.
     * @return integer score to be awarded.
     */
    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        // on key press determine score from distance from target
        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE;
            }
        // miss if note out of bounds
        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE;
        }

        return NOT_SCORED;

    }

    /**
     * renders feedback messages on screen.
     */
    public void update() {
        frameCount++;
        // render feedback on screen
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth()/2.0 - ACCURACY_FONT.getWidth(currAccuracy)/2,
                    Window.getHeight()/2.0);
        }
    }
}
