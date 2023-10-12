import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class BombNote extends SpecialNote{
    public BombNote(int appearanceFrame) {
        super("Bomb", appearanceFrame);
    }
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
