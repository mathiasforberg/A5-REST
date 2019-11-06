import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHandler {

    /**
     * Read the whole content from an InputStream, return it as a string
     *
     * @param inputStream the InputStream that will be read from
     * @return The whole InputStream as a string
     */
    public String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        try {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
                response.append('\n');
            }
        } catch (IOException e) {
            System.out.println("Could not read the data from HTTP response: " + e.getMessage());
        }
        return response.toString();
    }
}