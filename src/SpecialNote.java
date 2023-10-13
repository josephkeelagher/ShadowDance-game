import bagel.*;

/**
 * Class for Special Notes that have effects on the game when activated.
 * @author Joseph Keelagher
 * Inspired/Used code from Sample Project 1
 */
public abstract class SpecialNote extends Note implements DrawableNote, Resettable, Updateable{
    protected int y = 100;
    protected boolean activated = false;
    protected Image image;

    /**
     * Constructor for Special Note. initializes a Special Note.
     * @param type the type of special note.
     * @param appearanceFrame when the note first appears on screen.
     */
    public SpecialNote(String type, int appearanceFrame) {
        super(appearanceFrame);
        image = new Image("res/note" + type + ".png");
    }

    /**
     * Deactivates SpecialNotes that have been pressed and their effect activated.
     * @param input Input from the keyboard.
     * @param effectHandler Object that handles certain effect interactions.
     * @param targetHeight Y-value of the on-screen target.
     * @param relevantKey Key designated to activating the effect.
     * @return String returns either null or effect activation phrase.
     */
    public abstract String checkEffect(Input input, EffectHandler effectHandler,
                                       int targetHeight, Keys relevantKey);

    /**
     * resets notes to be replayable.
     */
    @Override
    public void reset() {
        active = false;
        completed = false;
        y = 100;
    }

    /**
     * moves the note down by a speed factor.
     */
    @Override
    public void update() {
        if (active) {
            y += speed;
        }

        if (Level.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    /**
     * draws note on screen.
     * @param x the x value to be drawn at.
     */
    @Override
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
}
