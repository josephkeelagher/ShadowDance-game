import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Joseph Keelagher
 */
public class ShadowDance extends AbstractGame {
    // Inspired/Used code from Sample Project 1
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private static String CSV_FILE;
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int END_MSG_Y = 300;
    private final static int END_MSG_2_Y = 500;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int INS_LINE_2_OFFSET = 20;
    private final static int INS_LINE_3_OFFSET = 40;
    private final static int SCORE_LOCATION = 35;
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String INSTRUCTIONS = "SELECT LEVELS WITH";
    private static final String INS_LINE_2 = "NUMBER KEYS";
    private static final String INS_LINE_3 = "1 2 3";
    private static final int CLEAR_SCORE = 150;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String END_MESSAGE = "PRESS SPACE " +
            "TO RETURN TO SELECTION";
    private final Accuracy accuracy = new Accuracy();
    private final EffectHandler effectHandler = new EffectHandler();
    private static final String[] LEVELS = {"res/level1.csv", "res/level2.csv", "res/level3.csv"};
    private final int numLevels = 3;
    private Level[] levelArray = new Level[3];
    private Level level;

    /**
     * Constructor for ShadowDance, creates an instance of the game.
     */
    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        for (int i = 0; i < numLevels; i++) {
            levelArray[i] = new Level();
            readCSV(i);
        }
    }
    /**
     * Method used to read file and create objects.
     */
    private void readCSV(int levelNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(LEVELS[levelNum]))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[0].equals("Lane")) {
                    // reading lanes
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    levelArray[levelNum].addLane(laneType, pos);
                } else {
                    // reading notes
                    String dir = splitText[0];
                    Lane lane = null;
                    for (int i = 0; i < levelArray[levelNum].getNumLanes(); i++) {
                        if (levelArray[levelNum].getLane(i).getType().equals(dir)) {
                            lane = levelArray[levelNum].getLane(i);
                        }
                    }

                    if (lane != null) {
                        switch (splitText[1]) {
                            case "Normal":
                                NormalNote note = new NormalNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(note);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote (dir, Integer.parseInt(splitText[2]));
                                lane.addNote(holdNote);
                                break;
                            case "SpeedUp":
                                SpeedUpNote speedUpNote = new SpeedUpNote(Integer.parseInt(splitText[2]));
                                lane.addNote(speedUpNote);
                                break;
                            case "SlowDown":
                                SlowDownNote slowDownNote = new SlowDownNote(Integer.parseInt(splitText[2]));
                                lane.addNote(slowDownNote);
                                break;
                            case "DoubleScore":
                                DoubleScoreNote doubleScoreNote = new DoubleScoreNote(Integer.parseInt(splitText[2]));
                                lane.addNote(doubleScoreNote);
                                break;
                            case "Bomb":
                                BombNote bombNote = new BombNote(Integer.parseInt(splitText[2]));
                                lane.addNote(bombNote);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        System.out.println(Level.getCurrFrame());
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (!Level.isStarted()) {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
            INSTRUCTION_FONT.drawString(INS_LINE_2,
                    (Window.getWidth() - INSTRUCTION_FONT.getWidth(INS_LINE_2)) / 2.0,
                    TITLE_Y + INS_Y_OFFSET + INS_LINE_2_OFFSET);
            INSTRUCTION_FONT.drawString(INS_LINE_3,
                    (Window.getWidth() - INSTRUCTION_FONT.getWidth(INS_LINE_3)) / 2.0,
                    TITLE_Y + INS_Y_OFFSET + INS_LINE_3_OFFSET);
            if (input.wasPressed(Keys.NUM_1)) {
                level = levelArray[0];
                level.start();
            }
            if (input.wasPressed(Keys.NUM_2)) {
                level = levelArray[1];
                level.start();
            }
            if (input.wasPressed(Keys.NUM_3)) {
                level = levelArray[2];
                level.start();
            }
        } else if (level.isFinished()) {
            // end screen
            if (level.getScore() >= CLEAR_SCORE) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(CLEAR_MESSAGE)/2,
                        END_MSG_Y);
            } else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE)/2,
                        END_MSG_Y);
            }
            INSTRUCTION_FONT.drawString(END_MESSAGE,
                    WINDOW_WIDTH/2 - INSTRUCTION_FONT.getWidth(END_MESSAGE)/2,
                    END_MSG_2_Y);
            if (input.wasPressed(Keys.SPACE)) {
                accuracy.setAccuracy(null);
                level.reset();
            }
        }

        else {
            // gameplay

            SCORE_FONT.drawString("Score " + level.getScore(), SCORE_LOCATION, SCORE_LOCATION);

            if (level.isPaused()) {
                if (input.wasPressed(Keys.TAB)) {
                    level.resume();
                    //track.run();
                }
                level.drawLanes();

            } else {
                level.nextFrame();
                level.update(input, accuracy, effectHandler);
                if (input.wasPressed(Keys.TAB)) {
                    level.pause();
                }
            }
        }
    }
}
