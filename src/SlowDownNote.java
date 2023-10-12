import bagel.Input;
import bagel.Keys;

public class SlowDownNote extends SpecialNote{
    public SlowDownNote(int appearanceFrame) {
        super("SlowDown", appearanceFrame);
    }
    public String checkEffect(Input input, EffectHandler effectHandler,
                              int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // check if key press activated effect
            String effectHit = effectHandler.evaluateEffectHit(y, targetHeight, input.wasPressed(relevantKey));
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
