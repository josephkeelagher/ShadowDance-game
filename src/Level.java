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
    public void addLane(String laneType, int pos) {
        if (laneType.equals("Special")) {}
        else {
            lanes[numLanes] = new NormalLane(laneType, pos);
        }
        numLanes++;
    }
    public Lane getLane(int index) {
        return lanes[index];
    }
    public int getNumLanes() {
        return numLanes;
    }
    public boolean isStarted() {
        return started;
    }
    public static int getScore() {
        return score;
    }
    public static int getCurrFrame() {
        return currFrame;
    }
}
