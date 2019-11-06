import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse {

    public JSONArray objectParsing(String JSONString) {
        String jsonObjectString = JSONString;
        JSONArray bodyValue = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(jsonObjectString);
            if (jsonObject.has("arguments")) {
                bodyValue = jsonObject.getJSONArray("arguments");
            }
        } catch (JSONException e) {
            System.out.println("Exception in JSON parsing: " + e.getMessage());
        }
        return bodyValue;
    }
}
