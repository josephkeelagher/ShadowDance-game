import bagel.*;
public class EffectHandler {
    private static final int HIT_RADIUS = 50;
    public static final String SPEED_UP = "SPEED UP";
    public static final String DOUBLE_SCORE = "DOUBLE SCORE";
    public static final String LANE_CLEAR = "LANE CLEAR";
    public static final String SLOW_DOWN = "SLOW DOWN";
    public static final String HIT = "HIT";
    public static final String MISS = "MISS";
    private static final Font EFFECT_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currEffect = null;
    private int frameCount = 0;

    public void setEffect(String effect) {
        currEffect = effect;
        frameCount = 0;
    }

    public String evaluateEffectHit(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);
        if (triggered && (distance <= HIT_RADIUS)) {
            setEffect(SPEED_UP);
            return HIT;
        } else if (height >= (Window.getHeight())) {
            setEffect(Accuracy.MISS);
            return MISS;
        }
        return null;
    }

    public void update() {
        frameCount++;
        if (currEffect != null && frameCount < RENDER_FRAMES) {
            EFFECT_FONT.drawString(currEffect,
                    Window.getWidth()/2.0 - EFFECT_FONT.getWidth(currEffect)/2,
                    Window.getHeight()/2.0);
        }
    }



}
