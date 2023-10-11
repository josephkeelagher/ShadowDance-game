import bagel.*;
public class HoldNote extends Note{
    private static final int HEIGHT_OFFSET = 82;
    private int y = 24;
    private boolean holdStarted = false;

    public HoldNote(String type, int appearanceFrame) {
        super(type, appearanceFrame);
    }
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));

            if (score == Accuracy.MISS_SCORE) {
                deactivate();
                return score;
            } else if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }
        } else if (isActive() && holdStarted) {

            int score = accuracy.evaluateScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
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

    public void startHold() {
        holdStarted = true;
    }

    private int getBottomHeight() {
        return y + HEIGHT_OFFSET;
    }

    private int getTopHeight() {
        return y - HEIGHT_OFFSET;
    }
}
