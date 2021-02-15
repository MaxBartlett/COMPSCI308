/**
 * The Main class sets up the various levels objects of the game into place
 * in a JavaFX window and runs the game loop. The Main class is quite bloated
 * and could likely be broken up into several well-defined and well-designed constituent
 * parts. For example, a level class would help to organize the project considerably.
 *
 * Several aspects of this class are not fully functional. The lives feature does not currently work,
 * and several cheat codes and power ups, such as cheatCodeMoveBall and stickyPowerUp are sloppily designed
 * and do not work as intended.
 * @author Max Bartlett
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Main extends Application {
    public static final String TITLE = "Dukeout";
    public static final int WINDOW_WIDTH = 770;
    public static final int WINDOW_HEIGHT = 480;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String[] brickFiles = {"./levels/level1.txt", "./levels/level2.txt", "./levels/level3.txt"};

    private Scene titleScene, levelOneScene, levelTwoScene, levelThreeScene, gameOverScene;
    private Stage theStage;
    private Pane pane;
    private Paddle myPaddle;
    private Ball myBall;
    private BrickArrayList bricks;
    private int lives = 3;
    private boolean cheatCodeMoveBall = false;
    private boolean stickyPowerUp = false;
    private boolean pause = false;
    private Text HUD;

    /**
     * Sets the text denoting the number of lives on the screen
     */
    private void setText() {
        HUD = new Text("Lives: " + lives);
        HUD.setX(0);
        HUD.setY(WINDOW_HEIGHT);
        HUD.setFill(Color.WHITE);
        HUD.setFont(Font.font ("Verdana", 20));
    }

    BackgroundImage titleScreenBackground = new BackgroundImage(new Image("titlescreen.gif"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    BackgroundImage levelOneBackground = new BackgroundImage(new Image("chapel.gif"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    BackgroundImage levelTwoBackground = new BackgroundImage(new Image("bryancenter.gif"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    BackgroundImage levelThreeBackground = new BackgroundImage(new Image("grosshall.gif"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    BackgroundImage gameOverBackground = new BackgroundImage(new Image("gameover.gif"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    /**
     * Sets up all of the scenes
     */
    private void setupScenes() {
        titleScene = setupTitle(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        levelOneScene = setupLevelOne(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        gameOverScene = setupGameOver(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
    }

    /**
     * Sets up title scene
     */
    private void setupStage() {
        theStage.setScene(titleScene);
        theStage.setTitle(TITLE);
        theStage.show();
    }

    /**
     * Checks the bounds of the paddle and ball
     */
    private void checkBounds() {
        myPaddle.checkBounds(WINDOW_WIDTH, WINDOW_HEIGHT);
        myBall.checkBounds(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    /**
     * Sets the ball's coordinates if the game is not paused
     */
    private void setCoordinates() {
        if (!pause) {
            myBall.setCoordinates();
        }
        myPaddle.setCoordinates();
    }

    /**
     * Checks the ball's collision with the paddle and all of the bricks in the level
     */
    private void checkCollision() {
        if(myBall.checkPaddleCollision(myPaddle)) {
            if(stickyPowerUp) {
                pause = true;
            }
        }
        for (int j = bricks.getArrayList().size() - 1; j >= 0; j--) {
            if(myBall.checkBrickCollision(bricks.getArrayList().get(j))) {
                bricks.getArrayList().get(j).onCollision();
            }
        }
    }

    /**
     * Checks whether all blocks are destroyed and loads the next
     * scene if true
     */
    private void checkEndOfLevel() {
        if(bricks.allBricksDestroyed()) {
            if(theStage.getScene().equals(levelThreeScene)) {
                theStage.setScene(gameOverScene);
            }

            if(theStage.getScene().equals(levelTwoScene)) {
                levelThreeScene = setupLevelThree(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
                theStage.setScene(levelThreeScene);
            }

            if(theStage.getScene().equals(levelOneScene)) {
                levelTwoScene = setupLevelTwo(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
                theStage.setScene(levelTwoScene);
            }
        }
    }

    /**
     * Sets up the scenes and stage, and runs the game loop.
     * @param stage the given stage on which to setup the scenes
     */
    @Override
    public void start (Stage stage) {
        theStage = stage;
        setupScenes();
        setupStage();

        // attach scene to the stage and display it
        // attach "game loop" to timeline to play it
        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                checkBounds();
                setCoordinates();
                checkCollision();
                checkEndOfLevel();
            };
        };
        animator.start();
    }

    /**
     * Sets up the title screen scene
     * @param width width of the window
     * @param height height of the window
     * @param background background color of the window
     * @return The title screen scene object
     */
    private Scene setupTitle (int width, int height, Paint background) {
        pane = new Pane();
        pane.setBackground(new Background(titleScreenBackground));
        Scene scene = new Scene(pane, width, height);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        return scene;
    }

    /**
     * Initializes the various objects for a level given a levelFilePath
     * @param levelFilePath file path to a .txt file containing the level layout
     */
    private void setupLevelEntities(String levelFilePath) {
        myPaddle = new Paddle();
        myBall = new Ball();
        bricks = new BrickArrayList(levelFilePath);
        setText();
        pane.getChildren().add(myBall.getImageView());
        pane.getChildren().add(myPaddle.getImageView());
        for(int i = 0; i < bricks.getArrayList().size(); i++) {
            pane.getChildren().add(bricks.getArrayList().get(i).getImageView());
        }
        pane.getChildren().add(HUD);
        myPaddle.setInitialCoordinates(WINDOW_WIDTH, WINDOW_HEIGHT);
        myBall.setInitialCoordinates(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    /**
     * Sets up the level 1 scene
     * @param width width of the window
     * @param height height of the window
     * @param background background color of the window
     * @return The level 1 Scene object
     */
    private Scene setupLevelOne (int width, int height, Paint background) {
        pane = new Pane();
        pane.setBackground(new Background(levelOneBackground));
        setupLevelEntities(brickFiles[0]);
        Scene scene = new Scene(pane, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return scene;
    }

    /**
     * Sets up the level 2 scene
     * @param width width of the window
     * @param height height of the window
     * @param background background color of the window
     * @return The level 2 Scene object
     */
    private Scene setupLevelTwo (int width, int height, Paint background) {
        pane = new Pane();
        pane.setBackground(new Background(levelTwoBackground));
        setupLevelEntities(brickFiles[1]);
        Scene scene = new Scene(pane, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return scene;
    }

    /**
     * Sets up the level 3 scene
     * @param width width of the window
     * @param height height of the window
     * @param background background color of the window
     * @return The level 3 Scene object
     */
    private Scene setupLevelThree (int width, int height, Paint background) {
        pane = new Pane();
        pane.setBackground(new Background(levelThreeBackground));
        setupLevelEntities(brickFiles[2]);
        Scene scene = new Scene(pane, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return scene;
    }

    /**
     * Sets up the GameOver scene
     * @param width width of the window
     * @param height height of the window
     * @param background background color of the window
     * @return The game over Scene object
     */
    private Scene setupGameOver (int width, int height, Paint background) {
        pane = new Pane();
        pane.setBackground(new Background(gameOverBackground));
        Scene scene = new Scene(pane, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        return scene;
    }

    /**
     * Sets the KeyCodes for various events.
     * The arrow keys control the paddle, and if the
     * cheatCodeMoveBall is active, they move the ball as well.
     *
     * Digits 0-4 warp load the titleScene, levels 1-3, and the gameOverScene, respectively
     *
     * The space bar begins the game, if on the titleScene, and controls the stickyPowerUp
     * if stickyPowerUp is true
     *
     * This method could likely be broken up into several smaller, more readable methods
     * @param code
     */
    private void handleKeyPress (KeyCode code) {
        if (code == KeyCode.UP) {
            if (cheatCodeMoveBall) {
                myBall.setY(myBall.getY() - (myBall.getBallSpeed() * 3));
            }
        }
        if (code == KeyCode.DOWN) {
            if (cheatCodeMoveBall) {
                myBall.setY(myBall.getY() + (myBall.getBallSpeed() * 3));
            }
        }
        if (code == KeyCode.RIGHT) {
            myPaddle.setXDirection(1);
            if (cheatCodeMoveBall) {
                myBall.setX(myBall.getX() + (myBall.getBallSpeed() * 3));
            }
            if(stickyPowerUp) {
                myBall.setX(myBall.getX() + myPaddle.getSpeed());
            }
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setXDirection(-1);
            if (cheatCodeMoveBall) {
                myBall.setX(myBall.getX() - (myBall.getBallSpeed() * 3));
            }
            if(stickyPowerUp) {
                myBall.setX(myBall.getX() - myPaddle.getSpeed());
            }
        }
        if (code == KeyCode.DIGIT0) {
            theStage.setScene(titleScene);
        }
        if (code == KeyCode.DIGIT1) {
            theStage.setScene(levelOneScene);
        }
        if (code == KeyCode.DIGIT2) {
            levelTwoScene = setupLevelTwo(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
            theStage.setScene(levelTwoScene);
        }
        if (code == KeyCode.DIGIT3) {
            levelThreeScene = setupLevelThree(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
            theStage.setScene(levelThreeScene);
        }
        if(code == KeyCode.DIGIT4) {
            theStage.setScene(gameOverScene);
        }
        if(code == KeyCode.SPACE) {
            if(theStage.getScene().equals(titleScene)) {
                theStage.setScene(levelOneScene);
            }

            if(stickyPowerUp) {
                pause = false;
            }
        }
        if(code == KeyCode.M) {
            cheatCodeMoveBall = !cheatCodeMoveBall;
        }
    }

    /**
     * If the left or right arrow keys are released, stop the paddle.
     * @param code the KeyCode to be read in
     */
    private void handleKeyRelease (KeyCode code) {
        if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
            myPaddle.setXDirection(0);
        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
