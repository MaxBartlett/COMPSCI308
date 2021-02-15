package shapes;

import javafx.scene.shape.Polygon;

public class HexagonCreator {
    public static final double RATIO_TRIANGLE_HEIGHT = Math.sqrt(3)/2;
    private Polygon myHex;

    public HexagonCreator(double leftCornX, double leftCornY, double sideLength){
        myHex = new Polygon();
        Double[] vertexes = {
                leftCornX, leftCornY,
                leftCornX + (sideLength/2), leftCornY + (RATIO_TRIANGLE_HEIGHT * sideLength),
                leftCornX + ((3 * sideLength)/2), leftCornY + (RATIO_TRIANGLE_HEIGHT * sideLength),
                leftCornX + (sideLength * 2), leftCornY,
                leftCornX + ((3 * sideLength)/2), leftCornY - (RATIO_TRIANGLE_HEIGHT * sideLength),
                leftCornX + (sideLength/2), leftCornY - (RATIO_TRIANGLE_HEIGHT * sideLength)
        };
        myHex.getPoints().addAll(vertexes);
    }

    public Polygon getMyHex(){
        return myHex;
    }
}
