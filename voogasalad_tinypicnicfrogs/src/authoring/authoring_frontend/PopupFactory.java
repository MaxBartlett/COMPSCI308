package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.PopupWindows.*;

class PopupFactory {
    private final static int PROTOTYPE_SIZE = 500;
    private final static int DIALOG_SIZE = 300;
    private final static int SAVE_SIZE = 400;
    private final static int LOAD_SIZE = 150;
    private boolean dark;

    public PopupFactory() {
        dark = true;
    }

    public PopupWindow getPopup(String type, GameManager manager, ActorManager actorManager, MapManager mapManager, String programName) {
        if("prototype".equalsIgnoreCase(type)) {
            return new PrototypeWindow(manager, PROTOTYPE_SIZE, dark, actorManager, mapManager);
        }
        else if("save".equalsIgnoreCase(type)) {
            return new SaveWindow(manager, SAVE_SIZE, dark);
        }
        else if("open".equalsIgnoreCase(type)){
            return new LoadWindow(manager, LOAD_SIZE, dark, actorManager, mapManager);
        }
        else if("editActors".equalsIgnoreCase(type)){
            return new PrototypeEditorWindow(manager, SAVE_SIZE, dark, actorManager, programName);
        }
        else if("dialogueKey".equalsIgnoreCase(type)){
            return new DialogueNodeWindow(manager, DIALOG_SIZE, dark);
        }
        else if("dialogueConnect".equalsIgnoreCase(type)){
            return new DialogueConnectionWindow(manager, DIALOG_SIZE, dark);
        }
        else if("setup".equalsIgnoreCase(type)){
            return new SetupProjectWindow(manager, DIALOG_SIZE, dark, mapManager);
        }
        else {
            return null;
        }
    }

    public void setDarkTheme(boolean isDark) {
        dark = isDark;
    }

    static PopupWindow getSetupPopup(GameManager manager, MapManager mapManager, boolean dark){
        return new SetupProjectWindow(manager, DIALOG_SIZE, dark, mapManager);
    }
}
