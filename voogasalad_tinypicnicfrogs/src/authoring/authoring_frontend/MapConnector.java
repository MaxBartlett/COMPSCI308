package authoring.authoring_frontend;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Connects different or same maps to create portals.
 *
 * @author Allen Qiu
 */
public class MapConnector {
    private Map firstMap;
    private Map secondMap;
    private Cell firstCell;
    private Cell secondCell;
    private StackPane firstCellPane;
    private StackPane secondCellPane;
    private MapManager mapManager;
    private int choosingMap = 1;

    /**
     * Constructor
     * @param manager MapManager of the environment.
     */
    MapConnector(MapManager manager){
        mapManager = manager;
        firstCell = null;
        secondCell = null;
        startConnector();
    }

    /**
     * Goes down the pathway of dialogs to connect maps.
     */
    public void startConnector(){
        firstMap = selectMapDialog();
        if(firstMap != null){
            selectSquareDialog(firstMap);
            if(firstCell != null){
                choosingMap = 2;
                secondMap = selectMapDialog();
                if(secondMap != null){
                    selectSquareDialog(secondMap);
                    if(secondCell != null){
                        //connect
                        mapManager.addPortal(new Portal(new Pair<>(firstCell.getX(), firstCell.getY()), firstMap, new Pair<>(secondCell.getX(), secondCell.getY()), secondMap, showReversableDialog(), mapManager));
                        //System.out.println("Added a portal from " + firstMap + " at (" + firstCell.getX() + ", " + firstCell.getY() + ") to " + secondMap + " at (" + secondCell.getX() + ", " + secondCell.getY() + ")!");
                    }
                }
            }
        }
    }

    /**
     * Gets a list of maps and prompts the user to choose one.
     * @return The Map selected by the user.
     */
    public Map selectMapDialog(){
        List<String> choices = mapManager.getMapList();
        if(choices.size() > 0){
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Choose a Map");
            dialog.setHeaderText("Choose a Map");
            dialog.setContentText("Choose map to connect:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()){
                return mapManager.getMap(result.get());
            }
            return null;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error: there are no maps!");

            alert.showAndWait();
            return null;
        }
    }

    /**
     * Prompts the user to select a square from a map.
     * @param selectFrom The map to select from.
     */
    public void selectSquareDialog(Map selectFrom){
        //BorderPane
        GridPane selectOne = new GridPane();
        ArrayList<ArrayList<Cell>> myCells = selectFrom.getGrid().getCells();
       // Cell[][] myCells = selectFrom.getGrid().getCells();
        for(int i=0;i<myCells.size();i++){
            for(int j=0;j<myCells.get(i).size();j++){
                StackPane thisCell = new StackPane();
                thisCell.setPrefSize(mapManager.getCellSize(), mapManager.getCellSize());
                thisCell.setStyle("-fx-border-color: black;");
                for(Actor a:myCells.get(i).get(j).getActors()){
                    thisCell.getChildren().add(a.getActorImage());
                }
                thisCell.setOnMouseClicked(event -> {
                    if(choosingMap == 1){
                        if(firstCell != null){
                            firstCellPane.setStyle("-fx-border-color: black;");
                        }
                        firstCellPane = thisCell;
                    }
                    else {
                        if(secondCell != null){
                            secondCellPane.setStyle("-fx-border-color: black;");
                        }
                        secondCellPane = thisCell;
                    }
                    thisCell.setStyle("-fx-border-color: blue;");
                    int x = selectOne.getColumnIndex(thisCell);
                    int y = selectOne.getRowIndex(thisCell);
                    setCell(x, y, myCells);
                });
                selectOne.add(thisCell, j, i);
            }
        }
        /*
        for(int i=0;i<myCells.length;i++){
            for(int j=0;j<myCells[i].length;j++){
                StackPane thisCell = new StackPane();
                thisCell.setPrefSize(18, 18);
                thisCell.setStyle("-fx-border-color: black;");
                for(Actor a:myCells[i][j].getActors()){
                    thisCell.getChildren().add(a.getActorImage());
                }
                thisCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        thisCell.setStyle("-fx-border-color: blue;");
                        int x = selectOne.getColumnIndex(thisCell);
                        int y = selectOne.getRowIndex(thisCell);
                        setCell(x, y, myCells);
                    }
                });
                selectOne.add(thisCell, i, j);
            }
        }
        */
        ScrollPane selectCell = new ScrollPane(selectOne);
        selectCell.setPrefViewportWidth(200);
        selectCell.setPrefViewportHeight(300);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Choose a square");
        alert.setHeaderText("Choose a square");
        alert.setContentText("Choose a square that you want the portal to be placed on.");
        alert.getDialogPane().setContent(selectCell);
        alert.showAndWait();

    }

    /**
     * Chooses the first and second cells.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param myCells The matrix of cells.
     */
    private void setCell(int x, int y, ArrayList<ArrayList<Cell>> myCells){
        if(choosingMap == 1){
            //firstCell = myCells[x][y];
            firstCell = myCells.get(x).get(y);
        }
        else {
            secondCell = myCells.get(x).get(y);
            //secondCell = myCells[x][y];
        }
    }

    /**
     * Prompts user if the portal is reversable.
     * @return Boolean containing whether the portal is reversable.
     */
    public boolean showReversableDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reversable portal?");
        alert.setHeaderText("Reversable portal?");
        alert.setContentText("Make the portal reversable?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        }
        return false;
    }
}
