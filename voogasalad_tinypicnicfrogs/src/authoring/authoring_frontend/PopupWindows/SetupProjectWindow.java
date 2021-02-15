package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.SaveForm;
import authoring.authoring_frontend.MapManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

/**
 * SaveWindow
 *
 * @author brookekeene
 */
public class SetupProjectWindow extends PopupWindow{
    private int size;
    private static final int PADDING_TEN = 10;
    private static final int PADDING_TWENTY = 20;
    private static final int PADDING_ONEFIFTY = 150;
    private MapManager mapManager;

    /**
     * Constructor
     */
    public SetupProjectWindow(GameManager manager, int n, boolean d, MapManager m) {
        super(manager, n, d);
        size = n;
        mapManager = m;

        //this.display(myResources.getString("GameInfo"));
        this.addContent();
    }

    /**
     * creates the UI elements needed to save a game
     */
    public void addContent() {
        setCellSizes();
    }

    private void setCellSizes(){
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Project Setup");
        dialog.setHeaderText("Enter the dimensions of the project:");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(PADDING_TEN);
        grid.setVgap(PADDING_TEN);
        grid.setPadding(new Insets(PADDING_TWENTY, PADDING_ONEFIFTY, PADDING_TEN, PADDING_TEN));
        TextField xSize = new TextField();
        xSize.setPromptText("Width");
        TextField ySize = new TextField();
        ySize.setPromptText("Height");
        TextField cellSize = new TextField();
        cellSize.setPromptText("Cell Size");

        grid.add(new Label("Width:"), 0, 0);
        grid.add(xSize, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(ySize, 1, 1);
        grid.add(new Label("Cell Size:"), 0, 2);
        grid.add(cellSize, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                ArrayList<String> results = new ArrayList<>();
                results.add(xSize.getText());
                results.add(ySize.getText());
                results.add(cellSize.getText());
                return results;
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();

        result.ifPresent(widthHeight -> {
            mapManager.setInitialWidth(Integer.parseInt(widthHeight.get(0)));
            mapManager.setInitialHeight(Integer.parseInt(widthHeight.get(1)));
            mapManager.setCellSize(Integer.parseInt(widthHeight.get(2)));
            //System.out.println(cellWidth);
        });
    }
}
