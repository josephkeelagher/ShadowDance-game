import bagel.*;

/**
 * Note that when activated speeds up the game.
 * @author Joseph Keelagher
 * Inspired/Used code from Sample Project 1
 */
public class SpeedUpNote extends SpecialNote{

    /**
     * Constructor for SpeedUpNote
     * @param appearanceFrame the frame the note first appears on screen.
     */
    public SpeedUpNote(int appearanceFrame) {
        super("SpeedUp", appearanceFrame);
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
                    EffectHandler.SPEED_UP);
            if (effectHit != null) {
                deactivate();
                if (effectHit.equals(EffectHandler.HIT)) {
                    return EffectHandler.SPEED_UP;
                }
            }
        }
        return null;
    }

}
