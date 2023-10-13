import bagel.*;
import java.util.ArrayList;

/**
 * Level of the game, contains different lanes and notes.
 * @author Joseph Keelagher
 * Inspired/Used code from Project 1 Solution.
 */
public class Level implements Resettable{
    private int numLanes = 0;
    private ArrayList<Lane> lanes = new ArrayList<Lane>();
    private static int score = 0;
    private static int currFrame = 0;
    private static boolean started = false;
    private static boolean finished = false;
    private static boolean paused = false;
    private int scoreMultiplier = 1;
    private int frameCount = 480;
    private static final int MULTIPLIER_FRAMES = 480;
    private String currEffect;

    /**
     * determines whether the level has any more notes left.
     * @return boolean true if it doesn't, false if it does.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * updates all lanes and notes within the lanes' position each frame, updates scoring and effects based on hits.
     * @param input Keyboard input
     * @param accuracy Object that handles hit timing.
     * @param effectHandler Object that handles effect interactions.
     */
    public void update(Input input, Accuracy accuracy, EffectHandler effectHandler) {
        for (int i = 0; i < numLanes; i++) {
            if (lanes.get(i) instanceof SpecialLane) {
                currEffect =  ((SpecialLane) lanes.get(i)).update(input, effectHandler);
                if (currEffect != null) {
                    if (currEffect.equals(EffectHandler.SPEED_UP)) {
                        speedUp();
                        score += 15;
                    }
                    if (currEffect.equals(EffectHandler.SLOW_DOWN)) {
                        slowDown();
                        score += 15;
                    }
                    if (currEffect.equals(EffectHandler.DOUBLE_SCORE)) {
                        doubleMultiplier();
                    }
                }
            }
            else {
                checkMultiplier();
                if (((NormalLane) lanes.get(i)).isBombed()) {
                    ((NormalLane) lanes.get(i)).clearLane();
                    ((NormalLane) lanes.get(i)).removeBombed();
                    effectHandler.setEffect(EffectHandler.LANE_CLEAR);
                }
                score += (scoreMultiplier)*((NormalLane) lanes.get(i)).update(input, accuracy);
            }
        }

        accuracy.update();
        effectHandler.update();
        finished = checkFinished();
    }

    private void doubleMultiplier() {
        scoreMultiplier = 2;
        frameCount = 0;
    }
    private void checkMultiplier() {
        frameCount++;
        if (scoreMultiplier == 2 && frameCount > MULTIPLIER_FRAMES) {
            scoreMultiplier = 1;
        }
    }

    /**
     * determines if the level is finished.
     * @return boolean true if yes, false if no.
     */
    public boolean checkFinished() {
        for (int i = 0; i < numLanes; i++) {
            if (!this.getLane(i).isFinished()) {
                return false;
            }
        }
        return true;
    }

    private void speedUp() {
        Note.doubleSpeed();
    }
    private void slowDown() {
        Note.normalSpeed();
    }

    /**
     * adds a lane to a level.
     * @param laneType the type of lane to be added.
     * @param pos the x position of the lane.
     */
    public void addLane(String laneType, int pos) {
        Lane newlane;
        if (laneType.equals("Special")) {
            newlane = new SpecialLane(pos);
        }
        else {
            newlane = new NormalLane(laneType, pos);
        }
        lanes.add(newlane);
        numLanes++;
    }

    /**
     * draws all lanes within the level.
     */
    public void drawLanes() {
        for (int i = 0; i < numLanes; i++) {
            lanes.get(i).draw();
        }
    }

    /**
     * gives a lane object from the level
     * @param index the position of the lane in the list.
     * @return Lane object specified.
     */
    public Lane getLane(int index) {
        return lanes.get(index);
    }

    /**
     * determines the number of lanes in the level.
     * @return int the number of lanes.
     */
    public int getNumLanes() {
        return numLanes;
    }

    /**
     * starts the level.
     */
    public void start() {
        started = true;
    }

    /**
     * ends the level
     */
    public void end() {
        started = false;
        finished = false;
    }

    /**
     * resets the level to be replayable.
     */
    public void reset() {
        currFrame = 0;
        score = 0;
        started = false;
        finished = false;
        paused = false;
        for (int i = 0; i < numLanes; i++) {
            lanes.get(i).reset();
        }
    }

    /**
     * determines whether the level has been started.
     * @return boolean true if yes, false if no.
     */
    public static boolean isStarted() {
        return started;
    }

    /**
     * pauses the level.
     */
    public void pause() {
        paused = true;
    }

    /**
     * unpauses the level
     */
    public static void resume() {
        paused = false;
    }

    /**
     * determines whether the level is paused,
     * @return boolean true if paused, false if not.
     */
    public static boolean isPaused() {
        return paused;
    }

    /**
     * gets the current score of the level.
     * @return int the level's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * determines the current frame the level is on.
     * @return int the level's current frame.
     */
    public static int getCurrFrame() {
        return currFrame;
    }

    /**
     * moves the level forward by one frame.
     */
    public void nextFrame() {
        currFrame++;
    }
}
