import bagel.*;
public class SpeedUpNote extends SpecialNote{
    public SpeedUpNote(int appearanceFrame) {
        super("SpeedUp", appearanceFrame);
    }
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
