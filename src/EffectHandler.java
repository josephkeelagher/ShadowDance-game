import bagel.*;

/**
 * Object that handles effect interactions and activations.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public class EffectHandler implements Updateable{
    private static final int HIT_RADIUS = 50;
    /**
     * Feedback for activating speed up note.
     */
    public static final String SPEED_UP = "SPEED UP";
    /**
     * Feedback for activating double score note.
     */
    public static final String DOUBLE_SCORE = "DOUBLE SCORE";
    /**
     * Feedback for activating bomb note.
     */
    public static final String LANE_CLEAR = "LANE CLEAR";
    /**
     * Feedback for activating slow down note.
     */
    public static final String SLOW_DOWN = "SLOW DOWN";
    /**
     * Feedback for hitting a note.
     */
    public static final String HIT = "HIT";
    /**
     * Feedback for missing a note.
     */
    public static final String MISS = "MISS";
    private static final Font EFFECT_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currEffect = null;
    private int frameCount = 0;

    /**
     * sets the feedback depending on effect activated.
     * @param effect the feedback message
     */
    public void setEffect(String effect) {
        currEffect = effect;
        frameCount = 0;
    }

    /**
     * Determines if a special note has been hit within the right range to activate.
     * @param height y value height of the note
     * @param targetHeight y value height of the target
     * @param triggered boolean of key press or not
     * @param type type of effect note
     * @return String determining a hit, miss or nothing happens.
     */
    public String evaluateEffectHit(int height, int targetHeight, boolean triggered, String type) {
        int distance = Math.abs(height - targetHeight);
        if (triggered && (distance <= HIT_RADIUS)) {
            setEffect(type);
            return HIT;
        } else if (height >= (Window.getHeight())) {
            setEffect(Accuracy.MISS);
            return MISS;
        }
        return null;
    }

    /**
     * renders the effect feedback on screen.
     */
    public void update() {
        frameCount++;
        if (currEffect != null && frameCount < RENDER_FRAMES) {
            EFFECT_FONT.drawString(currEffect,
                    Window.getWidth()/2.0 - EFFECT_FONT.getWidth(currEffect)/2,
                    Window.getHeight()/2.0);
        }
    }



}
