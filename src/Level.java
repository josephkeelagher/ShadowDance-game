import bagel.*;
public class Level {
    private static int numLanes = 0;
    private Lane[] lanes = new Lane[5];
    private static int score = 0;
    private static int currFrame = 0;
    private boolean started = false;
    private boolean finished = false;
    private boolean paused = false;

    public boolean isFinished() {
        return finished;
    }

    public void update(Input input, Accuracy accuracy) {
        for (int i = 0; i < numLanes; i++) {
            score += lanes[i].update(input, accuracy);
        }

        accuracy.update();
        finished = checkFinished();
    }

    private boolean checkFinished() {
        for (int i = 0; i < numLanes; i++) {
            if (!this.getLane(i).isFinished()) {
                return false;
            }
        }
        return true;
    }


    public void addLane(String laneType, int pos) {
        if (laneType.equals("Special")) {}
        else {
            lanes[numLanes] = new NormalLane(laneType, pos);
        }
        numLanes++;
    }
    public void drawLanes() {
        for (int i = 0; i < numLanes; i++) {
            lanes[i].draw();
        }
    }
    public Lane getLane(int index) {
        return lanes[index];
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
            lanes[i].reset();
        }
    }
    public boolean isStarted() {
        return started;
    }
    public void pause() {
        paused = true;
    }
    public void resume() {
        paused = false;
    }
    public boolean isPaused() {
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
