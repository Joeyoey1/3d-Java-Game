package com.barbarian.game;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

import com.barbarian.game.models.Player;
import com.barbarian.game.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestHandler {

    /**
     * This is for header negotiation
     */
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * This method sends a get request to the server.
     * @param extension this is the extension on the URL that the request will be using.
     * @throws Exception there are chances for errors so this could throw exception.
     */
    public static Player printSendGet(String extension) throws Exception {

        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu:8080/" + extension;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Player player = mapper.readValue(response.toString(), Player.class);
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(response.toString()).getAsJsonObject();

        //print result

        System.out.println();
        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");

        return player;
    }

    /**
     * This handles post requests when the post is in already in Json Object format.
     * @param extension the extension that is receiving the post request.
     * @throws Exception this code has the potential to throw 6 exception that I know of so I cast a wide net :/
     */
    public static boolean sendPost(String extension, User user) throws Exception {

        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu:8080/" + extension;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();



        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        String toSend = mapper.writeValueAsString(user);
        System.out.println(toSend);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(toSend);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
//
//        System.out.println();
//        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");
//        if(response.toString().equals("false")) return false;
        return true;
    }

}
