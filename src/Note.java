import bagel.*;

public abstract class Note {
    protected final int appearanceFrame;
    protected final int speed = 2;
    protected boolean active = false;
    protected boolean completed = false;

    public Note(String type, int appearanceFrame) {
        this.appearanceFrame = appearanceFrame;
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

    public abstract void update();
    public abstract void draw(int x);
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey);
}
