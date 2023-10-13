import bagel.Input;
import bagel.Keys;

/**
 * note that doubles scores awarded for a short period.
 */
public class DoubleScoreNote extends SpecialNote{
    // Inspired/Used code from Project 1 Solution.

    /**
     * Constructor for DoubleScoreNote
     * @param appearanceFrame frame the note first appears.
     */
    public DoubleScoreNote(int appearanceFrame) {
        super("2x", appearanceFrame);
    }

    /**
     * Deactivates SpecialNotes that have been pressed and their effect activated.
     * @param input Input from the keyboard.
     * @param effectHandler Object that handles certain effect interactions.
     * @param targetHeight Y-value of the on-screen target.
     * @param relevantKey Key designated to activating the effect.
     * @return String returns either null or effect activation phrase.
     */
    @Override
    public String checkEffect(Input input, EffectHandler effectHandler,
                              int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // check if key press activated effect
            String effectHit = effectHandler.evaluateEffectHit(y, targetHeight, input.wasPressed(relevantKey),
                    EffectHandler.DOUBLE_SCORE);
            if (effectHit != null) {
                deactivate();
                if (effectHit.equals(EffectHandler.HIT)) {
                    return EffectHandler.DOUBLE_SCORE;
                }
            }
        }
        return null;
    }
}
