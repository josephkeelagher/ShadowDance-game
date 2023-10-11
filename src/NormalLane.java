import bagel.*;

public class NormalLane extends Lane{
    private final Note[] notes = new NormalNote[100];
    private final Note[] holdNotes = new HoldNote[20];
    private int numHoldNotes = 0;
    private int currHoldNote = 0;

    public NormalLane(String type, int location) {
        super(type, location);
    }
    @Override
    public String getType() {
        return type;
    }

    @Override
    public void reset() {
        for (int i = 0; i < numNotes; i++) {
            notes[i].reset();
            currNote = 0;
        }
        for (int i = 0; i < numHoldNotes; i++) {
            holdNotes[i].reset();
            currHoldNote = 0;
        }
    }

    @Override
    public void addNote(Note note) {
        if (note instanceof NormalNote) {
            notes[numNotes++] = note;
        }
        if(note instanceof HoldNote) {
            holdNotes[numHoldNotes++] = note;
        }
    }

    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!notes[i].isCompleted()) {
                return false;
            }
        }

        for (int j = 0; j < numHoldNotes; j++) {
            if (!holdNotes[j].isCompleted()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < numNotes; i++) {
            notes[i].draw(location);
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes[j].draw(location);
        }
    }

    @Override
    public int update(Input input, Accuracy accuracy) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes[i].update();
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes[j].update();
        }

        if (currNote < numNotes) {
            int score = notes[currNote].checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (notes[currNote].isCompleted()) {
                currNote++;
                return score;
            }
        }

        if (currHoldNote < numHoldNotes) {
            int score = holdNotes[currHoldNote].checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes[currHoldNote].isCompleted()) {
                currHoldNote++;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }

}
