import bagel.*;
public abstract class Lane {
    protected final static int HEIGHT = 384;
    protected final static int TARGET_HEIGHT = 657;
    protected final Image image;
    protected int numNotes = 0;
    protected final String type;
    protected Keys relevantKey;
    protected final int location;
    protected int currNote = 0;

    public abstract String getType();
    public abstract int update(Input input, Accuracy accuracy);
    public abstract void addNote(Note note);
    public abstract void draw();
    public abstract boolean isFinished();
    public abstract void reset();
    public Lane(String type, int location) {
        this.type = type;
        this.location = location;
        image = new Image("res/lane" + type + ".png");
        switch (type) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }
}
