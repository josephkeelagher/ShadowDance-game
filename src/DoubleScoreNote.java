import bagel.Input;
import bagel.Keys;

public class DoubleScoreNote extends SpecialNote{
    public DoubleScoreNote(String type, int appearanceFrame) {
        super(type, appearanceFrame);
    }
    @Override
    public String checkEffect(Input input, EffectHandler effectHandler,
                              int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // check if key press activated effect
            boolean effectHit = effectHandler.evaluateEffectHit(y, targetHeight, input.wasPressed(relevantKey));
            if(effectHit) {
                deactivate();
                return EffectHandler.DOUBLE_SCORE;
            }
        }
        return null;
    }
}
