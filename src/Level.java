import bagel.*;
import java.util.ArrayList;
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

    public boolean isFinished() {
        return finished;
    }

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
    public void drawLanes() {
        for (int i = 0; i < numLanes; i++) {
            lanes.get(i).draw();
        }
    }
    public Lane getLane(int index) {
        return lanes.get(index);
    }
    public int getNumLanes() {
        return numLanes;
    }
    public void start() {
        started = true;
    }
    public void end() {
        started = false;
        finished = false;
    }
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
    public static boolean isStarted() {
        return started;
    }
    public void pause() {
        paused = true;
    }
    public static void resume() {
        paused = false;
    }
    public static boolean isPaused() {
        return paused;
    }
    public int getScore() {
        return score;
    }
    public static int getCurrFrame() {
        return currFrame;
    }
    public void nextFrame() {
        currFrame++;
    }
}
