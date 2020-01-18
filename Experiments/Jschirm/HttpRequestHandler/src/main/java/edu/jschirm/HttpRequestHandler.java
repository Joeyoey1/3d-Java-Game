package edu.jschirm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestHandler {

    /**
     * This is for header negotiation
     */
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpRequestHandler http = new HttpRequestHandler();

        System.out.println("Testing 1 - Send Http GET request");
        http.printSendGet("");

        System.out.println("\nTesting 2 - Send Http POST request");
        //http.sendPost("");

    }

    /**
     * This method sends a get request to the server.
     * @param extension this is the extension on the URL that the request will be using.
     * @throws Exception there are chances for errors so this could throw exception.
     */
    private void printSendGet(String extension) throws Exception {

        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu/" + extension;

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

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response.toString()).getAsJsonObject();

        //print result
        System.out.println(jsonObject.getAsString());

        System.out.println();
        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");

    }


    /**
     * This sends get requests and returns the fetched data
     * @param extension this is the extension on the URL that the request will be using.
     * @return JsonObject from get request
     * @throws Exception
     */
    public JsonObject sendGet(String extension) throws Exception
    {
        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu/" + extension;

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

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response.toString()).getAsJsonObject();

        //print result
        System.out.println(jsonObject.getAsString());

        System.out.println();
        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");

        return jsonObject;
    }


    /**
     * This handles post requests when the post is in Map<String, String> format.
     * @param extension this is the extension where the post is being sent.
     * @param convertJson this is the map that will be converted to Json
     * @throws Exception this code has the potential to throw 6 exception that I know of so I cast a wide net :/
     */
    private void sendPost(String extension, Map<String, String> convertJson) throws Exception {

        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu/" + extension;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();



        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        JsonObject toSend = new JsonObject();
        for (Map.Entry<String, String> alpha : convertJson.entrySet()) {
            toSend.addProperty(alpha.getKey(), alpha.getValue());
        }


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(toSend.getAsString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + toSend.getAsString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        System.out.println();
        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * This handles post requests when the post is in already in Json Object format.
     * @param extension the extension that is receiving the post request.
     * @param convertJson the Json Object to be sent.
     * @throws Exception this code has the potential to throw 6 exception that I know of so I cast a wide net :/
     */
    private void sendPost(String extension, JsonObject convertJson) throws Exception {

        long start = System.currentTimeMillis();

        String url = "http://coms-309-jr-4.misc.iastate.edu/" + extension;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();



        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");




        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(convertJson.getAsString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + convertJson.getAsString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        System.out.println();
        System.out.println("Response Time: " + (System.currentTimeMillis() - start) + "ms");
    }

}
