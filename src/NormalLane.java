import bagel.*;

public class NormalLane extends Lane{
    private final String type;
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
    public void addNote(Note note) {
        notes[numNotes++] = n;
    }

    @Override
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
    public void draw(int x, int y) {
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
