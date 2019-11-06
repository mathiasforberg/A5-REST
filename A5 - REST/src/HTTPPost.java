import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPPost {

    // Base URL (address) of the server
    private String BASE_URL;

    /**
     * Constructor for HTTPPost, used to initialise HTTPPost
     *
     * @param host the host the the request will be sent too (IP address or domain)
     * @param port the port that will be used
     */
    public HTTPPost(String host, int port) {
        this.BASE_URL = "http://" + host + ":" + port + "/";
    }

    public String sendPostRequest(String path, JSONObject jsonData) {
        return postRequest(path, jsonData);
    }

    /**
     *
     *
     * @param path the path that the HTTP get request will be sent to
     * @param jsonData the JSON data that will be sent in the post request
     * @return the response from the server
     */
    private String postRequest(String path, JSONObject jsonData) {
        String responseBody = "";
        try {
            String url = BASE_URL + path;
            URL urlObject = new URL(url);
            System.out.println("Sending HTTP post to: " + url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonData.toString().getBytes());
            outputStream.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Server reached");

                InputStream inputStream = connection.getInputStream();
                StreamHandler streamHandler = new StreamHandler();
                responseBody = streamHandler.convertStreamToString(inputStream);
                outputStream.close();
                System.out.println("Response from server");
                System.out.println(responseBody);
            } else {
                String responseDescription = connection.getResponseMessage();
                System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
            }
        } catch (MalformedURLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return responseBody;
    }
}
