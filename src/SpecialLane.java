import bagel.*;
import java.util.ArrayList;
public class SpecialLane extends Lane {
    private final String type = "Special";
    private final ArrayList<SpecialNote> notes = new ArrayList<SpecialNote>();

    public SpecialLane(int location) {
        super("Special", location);
    }
    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!notes.get(i).isCompleted()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addNote(Note note) {
        notes.add((SpecialNote) note);
        numNotes++;
    }

    @Override
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < numNotes; i++) {
                notes.get(i).draw(location);
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < numNotes; i++) {
            notes.get(i).reset();
            currNote = 0;
        }
    }

    public String update(Input input, EffectHandler effectHandler) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).update();
        }
        if (currNote < numNotes) {
            String effect = notes.get(currNote).checkEffect(input, effectHandler, TARGET_HEIGHT, Keys.SPACE);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return effect;
            }
        }
        return null;
    }
}
