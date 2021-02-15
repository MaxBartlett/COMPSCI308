package player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileManager {
    private boolean playerLoggedIn;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userBio;
    private ServerManager myManager;
    private List<String> followers;
    private List<String>following;
    private List<String>gamesCreated;
    private List<String>gamesPlayed;

    public UserProfileManager(){
        myManager= new ServerManager();
        playerLoggedIn=false;
        followers= new ArrayList<>();
        following=new ArrayList<>();
        gamesCreated=new ArrayList<>();
        gamesPlayed=new ArrayList<>();
    }



    public void login(String email, String password) throws ServerException {
        try {
            JSONObject response = myManager.login(email,password);
            userEmail=(String)response.get("email");
            userPassword=password;
            userBio=(String)response.get("bio");
            userName=(String)response.get("name");
            parseArray((JSONArray) response.get("followers"),followers);
            parseArray((JSONArray)response.get("following"),following);
            parseArray((JSONArray) response.get("gamesCreated"),gamesCreated);
            parseArray((JSONArray)response.get("gamesPlayed"),gamesPlayed);
            playerLoggedIn = true;
            System.out.println(userBio);
        }catch (IOException e){throw new ServerException(e);}
        catch (InterruptedException e){throw new ServerException(e);} catch (ParseException e) {
            throw new ServerException(e);
        }
    }

    public void register(String email,String password, String bio, String name) throws ServerException {
        try{
            myManager.register(email, password, bio, name);
            login(email,password);

        }catch (InterruptedException e){throw new ServerException(e);} catch (IOException e) {
            throw new ServerException(e);
        }
    }
    public Map<String,String> getUserAttributes(){
        Map<String,String>userData=new HashMap<>();
        userData.put("email",userEmail);
        userData.put("name",userName);
        userData.put("bio",userBio);
        return userData;
    }
    public void clear(){
        userEmail=null;
        userBio=null;
        userPassword=null;
        playerLoggedIn=false;
    }
    public List<String>getFollowers(){return followers;}
    public List<String>getFollowing(){return following;}
    public List<String>getGamesCreated(){return gamesCreated;}
    private List<String>getGamesPlayed(){return gamesPlayed;}
    public boolean isPlayerLoggedIn(){return playerLoggedIn;}
    public void parseArray(JSONArray arr,List<String>list){
        list.clear();
        if(arr==null||arr.size()==0){return;}
        for(int i=0;i<arr.size();i+=1){
            list.add((String)arr.get(i));
        }
    }
    public void updateInfo(String name, String bio) throws ServerException {
        try {
            JSONObject data=myManager.updateUser(userEmail,userPassword,bio,name);
            userBio=(String)data.get("bio");
            userName=(String)data.get("name");

        } catch (Exception e) {
            throw new ServerException(e);
        }

    }
    public List<JSONObject>lookUpUsers(String name) throws ServerException {
        List<JSONObject>users= new ArrayList<>();
        try {
            JSONArray arr = myManager.lookUpUsers(name);
            if(arr==null||arr.size()==0){return users;}
            for(int i=0;i<arr.size();i+=1){
                users.add((JSONObject)arr.get(i));
            }
        } catch (Exception e) {
            throw new ServerException(e);
        }
        return users;
    }
    public void followUser(String targetEmail) throws ServerException {
        try {
            JSONArray arr= myManager.followUser(targetEmail,userEmail);
            parseArray(arr,following);
        } catch (Exception e) {
            throw new ServerException(e);
        }
    }
}

