import org.json.JSONObject;

public class Grading {

    private static String eMail = "mathiagf@stud.ntnu.no";
    private static String phoneNumber = "91570518";

    public static void main(String[] args) {
        HTTPPost HTTPpost = new HTTPPost("datakomm.work", 80);
        HTTPpost.sendPostRequest("dkrest/auth", gradingInformation());
    }

    public static JSONObject gradingInformation() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", eMail);
        jsonObject.put("phone", phoneNumber);
        return jsonObject;
    }
}
