import bagel.*;
public class NormalNote extends Note{
    private int y = 100;

    public NormalNote(String type, int appearanceFrame) {
        super(type, appearanceFrame);
    }

    private int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int score = accuracy.evaluateScore(y, targetHeight, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }

    @Override
    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
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