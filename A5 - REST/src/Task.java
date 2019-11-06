import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Task {


    public static final String DATAKOMM_WORK = "datakomm.work";
    private static String sessionId;

    public static void main(String[] args) {
        HTTPPost httpPost = new HTTPPost(DATAKOMM_WORK, 80);
        JSONObject token = new JSONObject(httpPost.sendPostRequest("dkrest/auth", Grading.gradingInformation()));
        sessionId = token.get("sessionId").toString();
        HTTPGet httpGet1 = new HTTPGet(DATAKOMM_WORK, 80);
        httpGet1.sendGetRequest("dkrest/gettask/1?sessionId=" + sessionId);
        HTTPPost httpPost1 = new HTTPPost(DATAKOMM_WORK, 80);
        httpPost1.sendPostRequest("dkrest/solve", task1Object());
        HTTPPost httpPost2 = new HTTPPost(DATAKOMM_WORK, 80);
        httpPost2.sendPostRequest("dkrest/solve", task2Object());
        HTTPPost httpPost3 = new HTTPPost(DATAKOMM_WORK, 80);
        httpPost3.sendPostRequest("dkrest/solve", task3Object());
        HTTPPost httpPost4 = new HTTPPost(DATAKOMM_WORK, 80);
        httpPost4.sendPostRequest("dkrest/solve", task4Object());
        HTTPPost httpPost5 = new HTTPPost(DATAKOMM_WORK, 80);
        httpPost5.sendPostRequest("dkrest/solve", task5object());
        HTTPGet httpGet5 = new HTTPGet("datakomm.work", 80);
        httpGet5.sendGetRequest("dkrest/results/" + sessionId);
    }

    private static JSONObject task1Object() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("msg", "Hello");
        return jsonObject;
    }

    private static JSONObject task2Object() {
        JSONObject jsonObject = new JSONObject();
        JSONParse jsonParse = new JSONParse();
        HTTPGet httpGet2 = new HTTPGet(DATAKOMM_WORK, 80);
        String serverResponse = httpGet2.sendGetRequest("dkrest/gettask/2?sessionId=" + sessionId);
        JSONArray receivedMessage = jsonParse.objectParsing(serverResponse);
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("msg", receivedMessage.get(0));
        return jsonObject;
    }

    private static JSONObject task3Object() {
        JSONObject jsonObject = new JSONObject();
        JSONParse jsonParse = new JSONParse();
        HTTPGet httpGet3 = new HTTPGet(DATAKOMM_WORK, 80);
        String serverResponse = httpGet3.sendGetRequest("dkrest/gettask/3?sessionId=" + sessionId);
        JSONArray receivedMessage = jsonParse.objectParsing(serverResponse);

        int calculatedNumber = 1;
        for (int i = 0; i < receivedMessage.length(); i++ ) {
            calculatedNumber *= receivedMessage.getInt(i);
        }

        jsonObject.put("sessionId", sessionId);
        jsonObject.put("result",calculatedNumber);
        return jsonObject;
    }

    private static JSONObject task4Object() {
        JSONObject jsonObject = new JSONObject();
        JSONParse jsonParse = new JSONParse();
        HTTPGet httpGet4 = new HTTPGet(DATAKOMM_WORK, 80);
        String serverResponse = httpGet4.sendGetRequest("dkrest/gettask/4?sessionId=" + sessionId);
        JSONArray receivedMessage = jsonParse.objectParsing(serverResponse);
        String password = receivedMessage.get(0).toString();

        boolean run = true;
        String pin = null;
        int num = 0;
        String decodedpin = null;

        while(run){
            if(num<10){
                pin = "000" + num;
                decodedpin = decodePin(pin);
            }
            else if((num<100) && (num>9)){
                pin = "00" + num;
                decodedpin = decodePin(pin);
            }
            else if((num<1000) && (num>99)){
                pin = "0" + num;
                decodedpin = decodePin(pin);
            }
            else{
                pin = "" + num;
                decodedpin = decodePin(pin);
            }
            if(password.equals(decodedpin)){
                run = false;
            }
            num++;
        }
        System.out.println(pin);

        jsonObject.put("sessionId", sessionId);
        jsonObject.put("pin", pin);
        return jsonObject;
    }

    private static JSONObject task5object() {
        JSONObject jsonObject = new JSONObject();
        JSONParse jsonParse = new JSONParse();
        HTTPGet httpGet5 = new HTTPGet(DATAKOMM_WORK, 80);
        String serverResponse = httpGet5.sendGetRequest("dkrest/gettask/2016?sessionId=" + sessionId);
        JSONArray receivedMessage = jsonParse.objectParsing(serverResponse);

        IPv4 iPv4 = new IPv4(receivedMessage.get(0).toString(), receivedMessage.get(1).toString());
        String availableIp = iPv4.getAvailableIPs(2).get(0);

        jsonObject.put("sessionId", sessionId);
        jsonObject.put("ip", availableIp);
        return jsonObject;
    }

    /**
     *
     * @param pin pin to convert to md5
     * @return
     */
    private static String decodePin(String pin){
        String md5Pin = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(pin.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes){
                sb.append(String.format("%02x", b));
            }
            md5Pin = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Pin;
    }

}
