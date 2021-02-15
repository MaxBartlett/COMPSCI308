package authoring.authoring_backend;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.backend.AnimationObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Janice Liu
 * Purpose: Breaks up the map into squares for author use, calculate global coordinates. Everything is square
 */

public class MapManager {
    List<Integer> mapDimensions;
    int squareHeight;
    int squareWidth;
    int mapWidth;
    int mapHeight;

    protected MapManager() {
        mapDimensions = new ArrayList<>();
    }

    /**
     * The purpose is to divide the parameters given by the author into squares, and then returns the numbers to the
     * GameManager
     *
     * @param width
     * @param height
     * @param
     * @return
     */

    public Integer divideMap(int width, int height, int rows, int cols) {

        int totalSquares = rows * cols;
        mapHeight=height;
        mapWidth=width;
        squareHeight = height / cols;
        squareWidth = width / rows;
        return totalSquares;

    }

    /**
     * Whenever the author places an ActorPrototype onto the map in a specific square, this method will return the
     * coords with respect to the entire map instead of just the square. Uses the squareNum to get the relative position
     * from the whole map
     * @return
     */

    //everything initiated wrt the the top left corner
    //this is for the game engine, which does everything by pixels

    public int[] calculateGlobal(int X, int Y) {

        int[] global = new int[2];
        int globalX =  X;
        int globalY =  Y;
        System.out.println(X+","+Y);

        global[0] = globalX;
        global[1] = globalY;

        return global;
    }

    protected void serializeMap(String path)throws SaveException{
        XStream serializer = new XStream(new DomDriver());
        mapDimensions = new ArrayList<>();
        mapDimensions.add(getMapWidth());
        mapDimensions.add(getMapHeight());
        mapDimensions.add(getSquareSize());
        serializer.omitField(AnimationObject.class,"MapDimensions");
        String serialized= serializer.toXML(mapDimensions);

        try{

            Files.write(Paths.get(path+"map.xml"),serialized.getBytes());}catch (IOException e){throw new SaveException();}
    }

    protected void loadMap(String path){
        XStream serializer = new XStream(new DomDriver());
        List<Integer> mapDim =(List<Integer>) serializer.fromXML(Paths.get(path).toFile());
        mapDimensions.clear();
        mapWidth = mapDim.get(0);
        mapHeight = mapDim.get(1);
        squareHeight = mapDim.get(2);
        for(int i: mapDim){
            mapDimensions.add(i);
        }
    }

    protected int getMapWidth(){return mapWidth;}
    protected int getMapHeight(){return mapHeight;}
    protected int getSquareSize(){return squareHeight;}
}
