package engine.frontend.game_engine_UI.AnimationProcesser;

import javafx.scene.image.Image;

/**
 * SpriteProcessor class that will return an array of sprite from an input of ImageView
 *
 * @author Duy Trieu (dvt5)
 */
public class SpriteProcessor {
    private Image mySprite;
    private int row;
    private int col;
    private Sprite[] viewList;

    /**
     * @param sprite the whole sprite sheet
     * @param row the number of row in the sheet
     * @param col the number of column in the sheet
     */
    public SpriteProcessor(Image sprite, int row, int col) {
        this.mySprite = sprite;
        this.row = row;
        this.col = col;
        process();
    }

    /**
     * method that process the sprite sheet and put the sprite inside the array
     */
    private void process () {
        double width = mySprite.getWidth();
        double height = mySprite.getHeight();
        double x = width / col;
        double y = height / row;
        viewList = new Sprite[row*col];
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                Sprite rec = new Sprite(j*x,i*y,x,y);
                viewList[j + i*row] = rec;
            }
        }
    }

    /**
     * @return viewList
     */
    public Sprite[] getViewList () {
        return viewList;
    }
}
