import bagel.*;
import java.util.ArrayList;
public class SpecialLane extends Lane {
    private final String type = "Special";
    private final ArrayList<SpecialNote> notes = new ArrayList<SpecialNote>();
    private String effect;
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

    public String update(Input input, EffectHandler effectHandler) {
        draw();

        for (int i = currNote; i < numNotes; i++) {
            notes.get(i).update();
        }
        if (currNote < numNotes) {
            effect = notes.get(currNote).checkEffect(input, effectHandler, TARGET_HEIGHT, Keys.SPACE);
        }

    }
}
