//package authoring.authoring_backend;
//
//import javafx.stage.FileChooser;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.File;
//import java.io.FileReader;
//
//public class Tester {
//    public static void main(String[]args){
//        //GENERATE OnVictory and OnDefeat Messages:
//        GameManager gameManager = new GameManager();
//        gameManager.createMessage("onVictory","Player won");
//        gameManager.createMessage("onDefeat","Game over");
//        JSONObject data = loadJSON();
//        if(data!=null){
//            gameManager.createActorPrototype(data);
//            gameManager.createActor("charizard",0,0,0, 1, 1);
//            gameManager.saveGame("./resources/","./resources/authoring/");
//        }
//    }
//        private static JSONObject loadJSON(){
//            JSONParser parser = new JSONParser();
//
//            try{
//                JSONObject obj = (JSONObject) parser.parse(new File("/Users/janice/Code/CS308/voogasalad_tinypicnicfrogs/resources/TestPrototype.json").toString());
//               // System.out.println("JSON:"+obj.toJSONString());
////                JSONObject obj=(JSONObject) parser.parse("{\n" +
////                        "  \"name\":\"charizard\",\n" +
////                        "  \"animations\":[{\"key\":\"default\",\"path\":\"/resource/charizard1.png\"},{\"key\":\"special\",\"path\":\"/resource/charizard2.png\"}],\n" +
////                        "  \"stats\":[{\"key\":\"health\",\"value\":50},{\"key\":\"damage\",\"value\":5}\n" +
////                        "  ],\n" +
////                        "  \"Interactions\": [{\n" +
////                        "    \"name\":\"fight1\",\n" +
////                        "    \"type\":\"fight\",\n" +
////                        "    \"animations\":[{\"key\":\"default\",\"path\":\"/resource/charizard3.png\"},{\"key\":\"special\",\"path\":\"/resource/charizard4.png\"}],\n" +
////                        "    \"Messages\":[\n" +
////                        "      {\"key\":\"prototypeVictory\",\"messageKey\":\"onVictory\"},\n" +
////                        "      {\"key\":\"prototypeDefeat\",\"messageKey\":\"onDefeat\"}\n" +
////                        "    ],\n" +
////                        "\n" +
////                        "    \"moves\":[\n" +
////                        "      {\n" +
////                        "        \"name\":\"basic attack\",\n" +
////                        "        \"targetStat\": \"HP\",\n" +
////                        "        \"targetActorNumber\":1,\n" +
////                        "        \"targetActorType\":\"enemy\",\n" +
////                        "        \"targetValue\":10,\n" +
////                        "        \"targetType\":\"constant\"\n" +
////                        "      },{\n" +
////                        "        \"name\":\"basic regen\",\n" +
////                        "        \"targetStat\": \"HP\",\n" +
////                        "        \"targetActorNumber\":1,\n" +
////                        "        \"targetActorType\":\"friend\",\n" +
////                        "        \"targetValue\":10,\n" +
////                        "        \"targetType\":\"percent\"\n" +
////                        "      },{\n" +
////                        "        \"name\":\"special attack\",\n" +
////                        "        \"targetStat\": \"HP\",\n" +
////                        "        \"targetActorNumber\":3,\n" +
////                        "        \"targetActorType\":\"enemy\",\n" +
////                        "        \"targetValue\":10,\n" +
////                        "        \"targetType\":\"percent\"\n" +
////                        "      }\n" +
////                        "    ]\n" +
////                        "\n" +
////                        "  }\n" +
////                        "  ]\n" +
////                        "}");
//                return obj;
//            }catch (ParseException e){
//                e.printStackTrace();
//            }
//
//
//            return null;
//    }
//}
