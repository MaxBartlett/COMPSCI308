package shapes;

import javafx.scene.shape.Polygon;

public class TriangleCreator {
    public static final int VERTEX_NUM = 6;
    public static final double RATIO_TRIANGLE_HEIGHT = Math.sqrt(3)/2;
    private Polygon myTriangle;

    public TriangleCreator(double leftCornX, double leftCornY, double sideLength, int direction){
        myTriangle = new Polygon();
        Double[] vertexes = {
                leftCornX, leftCornY,
                leftCornX + sideLength, leftCornY,
                (leftCornX + (sideLength/2)), leftCornY + (RATIO_TRIANGLE_HEIGHT * sideLength * direction)
        };
        myTriangle.getPoints().addAll(vertexes);
    }

    public Polygon getMyTriangle(){
        return myTriangle;
    }
}
