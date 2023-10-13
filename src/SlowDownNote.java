import bagel.Input;
import bagel.Keys;

/**
 * Note that slows down the speed of all other notes in the game.
 * @author Joseph Keelagher
 * Inspired/Used code from Sample Project 1
 */
public class SlowDownNote extends SpecialNote{

    /**
     * Constructor for SlowDownNote, initializes a new instance of the class
     * @param appearanceFrame The frame the note first appears.
     */
    public SlowDownNote(int appearanceFrame) {
        super("SlowDown", appearanceFrame);
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
                    EffectHandler.SLOW_DOWN);
            // remove activated effect note
            if (effectHit != null) {
                deactivate();
                if (effectHit.equals(EffectHandler.HIT)) {
                    return EffectHandler.SLOW_DOWN;
                }
            }
        }
        return null;
    }
}
