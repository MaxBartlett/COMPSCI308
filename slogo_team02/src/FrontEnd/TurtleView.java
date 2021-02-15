package FrontEnd;

import Backend.Turtle;
import Backend.TurtleManager;
import Backend.VariableTracker;
import javafx.event.EventHandler;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/*
    @author xp19
 */

public class TurtleView {
    private static final String TURTLE_IMAGE = "turtle_green.png";
    private static final int SIZE = 50;
    private static double heading = 90;
    private static TitledPane turtleStatusPane;
    private int id; // each turtle view will have to have a unique ID
    private ImageView turtleImageView;
    private boolean isActive = true;
    private Image turtleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(TURTLE_IMAGE));
    private TurtleManager turtleManager;
    private boolean isCurrentlySelected;

    public TurtleView() {
        this.turtleManager = turtleManager;
        turtleImageView = new ImageView(turtleImage);
        turtleImageView.setFitWidth(SIZE);
        turtleImageView.setFitHeight(SIZE);
        turtleImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isActive) {
                    ColorAdjust colorAdjustGrayscale = new ColorAdjust();
                    colorAdjustGrayscale.setSaturation(-1);
                    turtleImageView.setEffect(colorAdjustGrayscale);
                    isActive = false;
                    turtleManager.getActiveTurtles().remove(turtleManager.getTurtleByID(id));
                } else {
                    turtleImageView.setEffect(null);
                    isActive = true;
                    turtleManager.getActiveTurtles().add(turtleManager.getTurtleByID(id));
                }
                isCurrentlySelected = true;
                turtleStatusPane.setContent(UIFactory.createTurtleStatusVBox(id, turtleManager.getTurtleByID(id).getX(),
                        turtleManager.getTurtleByID(id).getY(), turtleManager.getTurtleByID(id).getHeading()));
            }
        });
    }

    public TurtleView(VariableTracker variableTracker) {
        this();
        this.turtleManager = variableTracker.getTurtleManager();
    }

    public TurtleView(int id) {
        this();
        this.id = id;
    }

    public TurtleView(VariableTracker variableTracker, int id) {
        this();
        this.id = id;
        this.turtleManager = variableTracker.getTurtleManager();
    }

    public TurtleView(TurtleManager turtleManager, int id) {
        this();
        this.turtleManager = turtleManager;
        this.id = id;
    }

    public void setTurtleManager(TurtleManager turtleManager) {
        this.turtleManager = turtleManager;
    }

    public ImageView getTurtleImageView() {
        return turtleImageView;
    }

    // gives the x position of the turtle in the playground
    public double getX() {
        return turtleImageView.getLayoutX();
    }

    public double getY() {
        return turtleImageView.getLayoutY();
    }

    // update the position of the turtle to a new position
    public void update(double x, double y) {
        turtleImageView.setLayoutX(getX() + x);
        turtleImageView.setLayoutY(getY() - y);
    }

    public double getWidth() {
        return turtleImageView.getBoundsInLocal().getWidth();
    }

    public double getHeight() {
        return turtleImageView.getBoundsInLocal().getHeight();
    }

    public void rotate(double oldHeading, double newHeading) {
        turtleImageView.setRotate(turtleImageView.getRotate() - (newHeading - oldHeading));
//        RotateTransition rt = new RotateTransition(Duration.seconds(2));
//        rt.setToAngle(90);
//        rt.setByAngle(heading-newHeading);
        // put them together in order
//        Animation animation = new ParallelTransition(turtleImageView, rt);
//        animation.play();
//        heading = newHeading;
    }

    public void setTurtleImage(String fileName) {
        turtleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(fileName));
        turtleImageView.setImage(turtleImage);
    }

    public void setTurtleStatusPane(TitledPane turtleStatusPane) {
        this.turtleStatusPane = turtleStatusPane;
    }

    public void resetTurtleHeading() {
        turtleImageView.setRotate(0);
        heading = Turtle.DEFAULT_HEADING;
    }

    public void show() {
        turtleImageView.setVisible(true);
    }

    public void hide() {
        turtleImageView.setVisible(false);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Turtleview with id: " + id;
    }


}
