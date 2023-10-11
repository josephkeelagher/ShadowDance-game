import bagel.Input;
import bagel.Keys;

public class SlowDownNote extends SpecialNote{
    public SlowDownNote(String type, int appearanceFrame) {
        super(type, appearanceFrame);
    }
    public String checkEffect(Input input, EffectHandler effectHandler,
                              int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // check if key press activated effect
            boolean effectHit = effectHandler.evaluateEffectHit(y, targetHeight, input.wasPressed(relevantKey));
            if(effectHit) {
                deactivate();
                return EffectHandler.SLOW_DOWN;
            }
        }
        return null;
    }
}
