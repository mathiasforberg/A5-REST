import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Used to send a GET request to a server and read the response
 * sent back from the server.
 *
 * @author Mathias Gjærde Forberg
 * @version 1.0
 */
public class HTTPGet {

    //Base URL of the server (Address of the server)
    private String BASE_URL;

    /**
     * Constructor for HTTPGet, used to initialise HTTPGet
     *
     * @param host the host that the request will be sent too (IP address or domain)
     * @param port the port that will be used
     */
    public HTTPGet(String host, int port) {
        this.BASE_URL = "http://" + host + ":" + port + "/";
    }

    public String sendGetRequest(String path) {
        return getRequest(path);
    }

    /**
     * Sends an HTTP GET request to the specified path
     *
     * @param path the path that the HTTP get request will be sent to
     * @return the response from the server
     */
    private String getRequest(String path) {
        String responseBody = "";
        try {
            String url = BASE_URL + path;
            URL urlObject = new URL(url);
            System.out.println("Sending HTTP GET to " + url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Server reached");
                //Response was OK, read the body (data)
                InputStream inputStream = connection.getInputStream();
                StreamHandler streamHandler = new StreamHandler();
                responseBody = streamHandler.convertStreamToString(inputStream);
                inputStream.close();
                System.out.println("Response from the server");
                System.out.println(responseBody);
            } else {
                String responseDescription = connection.getResponseMessage();
                System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
            }
        } catch (MalformedURLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
        return responseBody;
    }
}
