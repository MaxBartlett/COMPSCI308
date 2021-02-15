/**
 * @author Max Bartlett
 *
 * BrickArrayList contains methods relating to a level's layout of bricks.
 * The main feature of the BrickArrayList is an ArrayList<Brick> that stores
 * a given level's brick layout from a .txt file.
 *
 * This .txt file must conform to a set of standards, namely, the
 * number of bricks laid out must not extend past the width and height
 * of the window, and the single character brick names must match those outlined
 * at the head of the file. Spaces will be interpreted as a blank spot, and newlines
 * will be interpreted as continuing the block layout on the next line below.
 * If a new type of brick is added, a new character must be added to the
 * head of this file to represent this brick, and the
 * constructor must be modified. This also assumes that all bricks are
 * 70 pixels x 20 pixels, and undefined behavior will occur
 * if larger bricks are included.
 *
 * BrickArrayList depends on Brick and its subclasses, and requires
 * modification should any new subclasses of Bricks be added.
 *
 * To use a BrickArrayList in the main class, call the constructor and
 * provide a well-formatted .txt file of the layout. Then add each element of
 * bricks to the scene.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BrickArrayList {
    private ArrayList<Brick> bricks;
    public static final int BRICK_WIDTH = 70;
    public static final int BRICK_HEIGHT = 20;
    private final char SPACE = ' ';
    private final int NEWLINE = 10; //10 is the integer ASCII value for newline
    private final int NORMAL_BRICK = 110;
    private final int TOUGH_BRICK = 116;
    private final int STONE_BRICK = 115;
    private final HashMap<Integer, Brick> brickTypesMap = new HashMap<>();
    /**
     * The sole constructor of BrickArrayList initializes the ArrayList<Brick>, bricks,
     * and reads in an appropriately-formatted .txt file denoting the
     * layout of the bricks. The constructor reads through the file
     * character by character, adding a Brick to bricks with given coordinates,
     * or incrementing the coordinate counters, i and j, depending on input.
     *
     * @param filePath the .txt file denoting the layout of the bricks, to be read in
     */


    public void makeBrickTypesMap() {
        asdf.put(NORMAL_BRICK, new NormalBrick());
        asdf.put(TOUGH_BRICK, new ToughBrick());
        asdf.put(STONE_BRICK, new StoneBrick());
    }

    public BrickArrayList(String filePath) {
        int ch, i = 0, j = 0;
        bricks = new ArrayList<>();
        Iterator it = asdf.entrySet().iterator();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((ch = br.read()) != -1) {
                for (HashMap.Entry<Integer, Brick> entry : brickTypesMap.entrySet())
                    if(ch == entry.getKey().intValue())
                        Brick brick = entry.getValue();
                        if ((char) ch == NORMAL_BRICK) {
                        bricks.add(new NormalBrick(i, j));
                    } else if ((char) ch == TOUGH_BRICK) {
                        bricks.add(new StoneBrick(i, j));
                    } else if ((char) ch == STONE_BRICK) {
                        bricks.add(new ToughBrick(i, j));
                    }
                }

                i += BRICK_WIDTH;

                if (ch == 10) {
                    j += BRICK_HEIGHT;
                    i = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Iterates through the ArrayList<Brick> bricks and checks whether or not all
     * bricks are destroyed. This is used in Main in determining whether the player has
     * completed the current level or not.
     *
     * @return A boolean denoting whether or not all bricks in bricks have been destroyed
     */
    public boolean allBricksDestroyed() {
        for(Brick brick : bricks) {
            if(!(brick instanceof StoneBrick) && !brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return The ArrayList<Brick> bricks
     */
    public ArrayList<Brick> getArrayList() {
        return bricks;
    }
}
