import bagel.Image;
import bagel.Input;
import bagel.Keys;

/**
 * bomb note that clears lanes when hit.
 * @author Joseph Keelagher
 */
public class BombNote extends SpecialNote{
    // Inspired/Used code from Project 1 Solution.

    /**
     * Constructor for BombNote
     * @param appearanceFrame frame the note first appears.
     */
    public BombNote(int appearanceFrame) {
        super("Bomb", appearanceFrame);
    }

    /**
     * Deactivates SpecialNotes that have been pressed and their effect activated.
     * @param input Input from the keyboard.
     * @param effectHandler Object that handles certain effect interactions.
     * @param targetHeight Y-value of the on-screen target.
     * @param relevantKey Key designated to activating the effect.
     * @return String returns either null or effect activation phrase.
     */
    public String checkEffect(Input input, EffectHandler effectHandler,
                              int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // check if key press activated effect
            String effectHit = effectHandler.evaluateEffectHit(y, targetHeight, input.wasPressed(relevantKey),
                    EffectHandler.LANE_CLEAR);
            if (effectHit != null) {
                deactivate();
                if (effectHit.equals(EffectHandler.HIT)) {
                    return EffectHandler.LANE_CLEAR;
                }
            }
        }
        return null;
    }
}
