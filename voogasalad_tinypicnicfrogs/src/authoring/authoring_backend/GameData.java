package authoring.authoring_backend;
/**
 * @author Michael Glushakov
 * Purpose: Stores high-level game data needed to load the game file
 * Dependencies: None
 * Usages: When game is saved the object is created, serialized and stored in games.xml file, which contains the map of all local games
 */

public class GameData {
    private String title;
    private String description;
    private String path;
    private int mapWidth,mapHeight, squareWidth, squareHeight;
    public GameData(String titleP,String descriptionP, String filePath,int mapW,int mapH,int squareWidthP, int squateHeightP) {//, int mapH,int mapW){
        title=titleP;
        description=descriptionP;
        path=filePath;
        mapHeight=mapH;
        mapWidth=mapW;
        squareWidth=squareWidthP;
        squareHeight=squateHeightP;
    }
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public String getPath(){return path;}
    public int getMapWidth(){return mapWidth;}
    public int getMapHeight(){return mapHeight;}
    public int getSquareWidth(){return squareWidth;}
    public int getSquareHeight(){return squareHeight;}
}
