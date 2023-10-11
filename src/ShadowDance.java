import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Joseph Keelagher
 */
public class ShadowDance extends AbstractGame {
    // some attributes from P1 Sample code.
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final static String CSV_FILE = "res/level1.csv";
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
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
    // private final Accuracy accuracy = new Accuracy();
    // private final Lane[] lanes = new Lane[4];
    private static final String LEVEL_1 = "level1.csv";
    private static final String LEVEL_2 = "level2.csv";
    private static final String LEVEL_3 = "level3.csv";
    private boolean paused = false;
    private Level level = new Level();


    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();
    }
    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    // Reads whichever file has been set as the CSV_FILE,
    // this will change with button presses at the home screen
    // or with completion of other levels.
    private void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[0].equals("Lane")) {
                    // reading lanes
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    level.addLane(laneType, pos);
                } else {
                    // reading notes
                    String dir = splitText[0];
                    Lane lane = null;
                    for (int i = 0; i < level.getNumLanes(); i++) {
                        if (level.getLane(i).getType().equals(dir)) {
                            lane = level.getLane(i);
                        }
                    }

                    if (lane != null) {
                        switch (splitText[1]) {
                            case "Normal":
                                NormalNote note = new NormalNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(note);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(holdNote);
                                break;
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

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (!level.isStarted()) {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
            INSTRUCTION_FONT.drawString(INS_LINE_2,
                    (Window.getWidth() - INSTRUCTION_FONT.getWidth(INS_LINE_2)) / 2.0,
                    TITLE_Y + INS_Y_OFFSET + INS_LINE_2_OFFSET);
            INSTRUCTION_FONT.drawString(INS_LINE_3,
                    (Window.getWidth() - INSTRUCTION_FONT.getWidth(INS_LINE_3)) / 2.0,
                    TITLE_Y + INS_Y_OFFSET + INS_LINE_3_OFFSET);

        } else {
            // gameplay

            SCORE_FONT.drawString("Score " + level.getScore(), SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    //track.run();
                }
            }
        }
    }
}
