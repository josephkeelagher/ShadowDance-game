import bagel.*;
import java.util.ArrayList;
public class NormalLane extends Lane{
    private final ArrayList<NormalNote> notes = new ArrayList<NormalNote>();
    private final ArrayList<HoldNote> holdNotes = new ArrayList<HoldNote>();
    private final ArrayList<BombNote> bombNotes = new ArrayList<BombNote>();
    private int numBombNotes = 0;
    private int currBombNote = 0;
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
            notes.get(i).reset();
            currNote = 0;
        }
        for (int i = 0; i < numHoldNotes; i++) {
            holdNotes.get(i).reset();
            currHoldNote = 0;
        }
    }

    @Override
    public void addNote(Note note) {
        if (note instanceof NormalNote) {
            notes.add((NormalNote) note);
            numNotes++;
        }
        if (note instanceof HoldNote) {
            holdNotes.add((HoldNote) note);
            numHoldNotes++;
        }
        if (note instanceof BombNote) {
            bombNotes.add((BombNote) note);
            numBombNotes++;
        }
    }

    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!notes.get(i).isCompleted()) {
                return false;
            }
        }

        for (int j = 0; j < numHoldNotes; j++) {
            if (!holdNotes.get(j).isCompleted()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).draw(location);
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes.get(j).draw(location);
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            bombNotes.get(k).draw(location);
        }
    }

    public int update(Input input, Accuracy accuracy) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).update();
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes.get(j).update();
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            bombNotes.get(k).update();
        }

        if (currNote < numNotes) {
            int score = notes.get(currNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return score;
            }
        }

        if (currHoldNote < numHoldNotes) {
            int score = holdNotes.get(currHoldNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes.get(currHoldNote).isCompleted()) {
                currHoldNote++;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }
    private void clearLane() {
        for (int i = currNote; i < numNotes; i++) {
            if (notes.get(i).isActive()) {
                notes.get(i).deactivate();
            }
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            if (holdNotes.get(j).isActive()) {
                holdNotes.get(j).deactivate();
            }
        }

        for (int k = currBombNote; k < numBombNotes; k++) {
            if (bombNotes.get(k).isActive()) {
                bombNotes.get(k).deactivate();
            }
        }
    }

}
