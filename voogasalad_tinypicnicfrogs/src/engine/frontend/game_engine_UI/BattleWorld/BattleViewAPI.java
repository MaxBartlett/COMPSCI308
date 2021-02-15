package engine.frontend.game_engine_UI.BattleWorld;

import engine.backend.AnimationObject;
import engine.backend.BattleTurn;
import engine.frontend.game_engine_UI.MenuView.BattleMenu;

public interface BattleViewAPI {
    void playMoveAnimation (AnimationObject animationObject, BattleTurn turn);
    BattleMenu getMenuView ();
}
