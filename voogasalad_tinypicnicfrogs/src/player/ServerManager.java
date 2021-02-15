package player;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;

/**
 * @author Michael Glushakov
 * Purpose: Contains methods for making HTTP requests to the server
 * Dependencies: Java's HTTP Client
 * Usages: Used by UserProfileManager to return server responses
 */
public class ServerManager {
    private static final String URL_LOCAL = "https://vooga-server.herokuapp.com";
    private static final String LOGIN_PATH="/login";
    private static final String REGISTER_PATH="/createuser";
    private static final String UPDATE_PATH="/updateProfile";
    private static final String LOOKUP_PATH="/findUsers";
    private static final String FOLLOW_PATH="/follow";
    private JSONParser parser;

    public ServerManager(){
        parser = new JSONParser();
    }

    /**
     * @param email
     * @param password
     * @return JSON of user data
     * @throws IOException handled by userProfileManager
     * @throws InterruptedException handled by userProfileManager
     */

    public JSONObject login(String email, String password) throws IOException, InterruptedException, ParseException {
        String body="{\n" +
                "\t\"user\":{\n" +
                "\t\t\"email\":\""+email+"\",\n" +
                "\t\t\"password\":\""+password+"\"\n" +
                "\t}\n" +
                "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL_LOCAL+LOGIN_PATH)) .header("Content-Type", "application/json").PUT(HttpRequest.BodyPublisher.fromString(body)).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());

        return(JSONObject)parser.parse(response.body());


    }

    /**
     *
     * @param email
     * @param password
     * @param bio
     * @param name
     * @throws IOException handled by userProfileManager
     * @throws InterruptedException handled by userProfileManager
     */

    public void register(String email, String password, String bio,String name ) throws IOException, InterruptedException {
        String body = "{\n" +
                "\t\"user\":{\n" +
                "\t\t\"email\":\""+email+"\",\n" +
                "\t\t\"password\":\""+password+"\",\n" +
                "\t\t\"bio\":\""+bio+"\",\n" +
                "\t\t\"name\":\""+name+"\"\n" +
                "\t}\n" +
                "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL_LOCAL+REGISTER_PATH)) .header("Content-Type", "application/json").POST(HttpRequest.BodyPublisher.fromString(body)).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
    }

    /**
     *
     * @param email
     * @param password
     * @param bio
     * @param name
     * @return JSON of user data
     * @throws IOException handled by userProfileManager
     * @throws InterruptedException handled by userProfileManager
     */
    public JSONObject updateUser(String email, String password, String bio,String name ) throws IOException, InterruptedException {
        String body = "{\n" +
                "\t\"user\":{\n" +
                "\t\t\"email\":\""+email+"\",\n" +
                "\t\t\"password\":\""+password+"\",\n" +
                "\t\t\"bio\":\""+bio+"\",\n" +
                "\t\t\"name\":\""+name+"\"\n" +
                "\t}\n" +
                "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL_LOCAL+UPDATE_PATH)) .header("Content-Type", "application/json").PUT(HttpRequest.BodyPublisher.fromString(body)).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        try {
            return(JSONObject)parser.parse(response.body());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param name
     * @return JSON of user data
     * @throws IOException handled by userProfileManager
     * @throws InterruptedException handled by userProfileManager
     */
    public JSONArray lookUpUsers(String name) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL_LOCAL+LOOKUP_PATH)) .header("Content-Type", "application/json").header("name",name).GET().version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        try {
            System.out.println(response.body());
            return(JSONArray) parser.parse(response.body());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param targetEmail
     * @param followerEmail
     * @return new following array
     * @throws IOException handled by userProfileManager
     * @throws InterruptedException handled by userProfileManager
     */
    public JSONArray followUser(String targetEmail, String followerEmail) throws IOException, InterruptedException {
        JSONObject body = new JSONObject();
        body.put("target",targetEmail);
        body.put("follower",followerEmail);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL_LOCAL+FOLLOW_PATH)) .header("Content-Type", "application/json").PUT(HttpRequest.BodyPublisher.fromString(body.toJSONString())).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        try {
            return (JSONArray) parser.parse(response.body());

        } catch (ParseException e) {
            return null;
        }

    }


}
