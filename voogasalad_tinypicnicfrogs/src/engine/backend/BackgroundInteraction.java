package engine.backend;

import org.json.simple.JSONObject;

import java.util.Map;


public class BackgroundInteraction extends Interaction {
    private boolean canPassThrough;
    public BackgroundInteraction(JSONObject data, Map<String, Message> messages){
        super(data,messages);
        canPassThrough=(boolean)data.get("canPassThrough");
    }
    protected boolean isCanPassThrough(){return canPassThrough;}
}
