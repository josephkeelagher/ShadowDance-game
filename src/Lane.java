import bagel.*
public abstract class Lane {
    protected final static int HEIGHT = 384;
    protected final static int TARGET_HEIGHT = 657;
    protected final Image image;
    protected final int numNotes = 0;
    protected final String type;
    protected final Keys relevantKey;
    protected final int location;
    protected final int currNote = 0;

    public abstract int update(Input input, Accuracy accuracy);
    public abstract void addNote(Note note);
    public abstract boolean isFinished();
    public abstract void draw(int x, int y);
    public abstract void clearLane();


}
