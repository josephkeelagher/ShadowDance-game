import bagel.*;
public class NormalNote extends Note{
    private int y = 100;

    public NormalNote(String type, int appearanceFrame) {
        super(type, appearanceFrame);
    }

    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
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

    public boolean isActive() {
        return active;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void deactivate() {
        active = false;
        completed = true;
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
