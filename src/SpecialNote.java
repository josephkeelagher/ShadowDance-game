import bagel.*;
public abstract class SpecialNote extends Note{
    protected int y = 100;
    protected boolean activated = false;
    protected Image image;
    public SpecialNote(String type, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/note" + type + ".png");
    }
    public abstract String checkEffect(Input input, EffectHandler effectHandler,
                                       int targetHeight, Keys relevantKey);
    @Override
    public void reset() {
        active = false;
        completed = false;
        y = 100;
    }

    @Override
    public void update() {
        if (active) {
            y += speed;
        }

        if (Level.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }
    @Override
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
}
