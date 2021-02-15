package FrontEnd;

import Backend.TurtleManager;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
    @author xp19
 */

public class TurtleViewManager implements Iterable<TurtleView> {

    public static int ID = 1;
    HashSet<Integer> ids;
    ArrayList<TurtleView> turtleList;
    VariableTracker variableTracker; // track the state of each turtle
    TurtleManager turtleManager;

    public TurtleViewManager(VariableTracker variableTracker) {
        this.variableTracker = variableTracker;
        this.turtleManager = variableTracker.getTurtleManager();
        turtleList = new ArrayList<>();
        ids = new HashSet<>();
        TurtleView view = new TurtleView(variableTracker, 1);
//        view.setTurtleStatusPane(turtleStatusTitledPane);
        addTurtleView(view);
//        this.turtlePlayground = turtlePlayground;
    }

    public void addTurtleView(TurtleView turtleView) {
        turtleList.add(turtleView);
        ID++;
    }

    public TurtleManager getTurtleManager() {
        return turtleManager;
    }

    @Override
    public Iterator<TurtleView> iterator() {
        return turtleList.iterator();
    }
}
