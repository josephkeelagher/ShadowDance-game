import bagel.Input;
import bagel.Keys;

public class DoubleScoreNote extends SpecialNote{
    public DoubleScoreNote(int appearanceFrame) {
        super("2x", appearanceFrame);
    }
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
