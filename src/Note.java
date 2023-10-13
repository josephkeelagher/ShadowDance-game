import bagel.*;

public abstract class Note implements DrawableNote, Resettable, Updateable{
    protected final int appearanceFrame;
    protected static int speed = 2;
    protected boolean active = false;
    protected boolean completed = false;

    public Note(int appearanceFrame) {
        this.appearanceFrame = appearanceFrame;
    }
    public abstract void reset();
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
    public int getAppearance() {
        return appearanceFrame;
    }
    public static void doubleSpeed() {
        speed = 4;
    }
    public static void normalSpeed() {
        speed = 2;
    }
    public abstract void update();
    public abstract void draw(int x);
}
