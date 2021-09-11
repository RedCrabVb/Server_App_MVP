import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.vivt.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {
    private static String token;
    private static boolean isRunning;

    @BeforeEach
    void serverStart() throws Exception {
        if (!isRunning) {
            Main.main(new String[]{});
            isRunning = true;

            serverRegistration();
        }
    }

    @AfterEach
    void endTest() throws Exception {
        Thread.sleep(50);//fix bug java, https://bugs.openjdk.java.net/browse/JDK-8214300
    }

    @Test
    void serverNewsGet() throws Exception {
        String api = "api/news";
        String result = sendInquiry(api, "");
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("News").toString().isEmpty());
        System.out.println(result);
    }

    @Test
    void serverRegistration() throws Exception {
        String api = "api/registration";
        String result = sendInquiry(api, "");
        token = JsonParser.parseString(result).getAsJsonObject().get("token").getAsString();
        System.out.println(result);
    }

    @Test
    void getQrCode() throws Exception {
        String api = "api/qrCode";
        String result = sendInquiry(api, "token=" + token);
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("qrCode").getAsString().isEmpty());
        System.out.println(result);
    }

    @Test
    void setPersonData() throws Exception {
        String api = "api/setPersonDate";
        String result = sendInquiry(api, String.format("token=%s&email=cany245",  token));
        System.out.println(result);
    }

    @Test
    void getStatusToken() throws Exception {
        String api = "api/getStatusToken";
        assertEquals(true, JsonParser.parseString(sendInquiry(api, String.format("token=%s",  token)))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    private String sendInquiry(String api, String json) throws Exception {
        json = json.replace("+", "%20"); // fix space encoder
        URL url = new URL(String.format("http://localhost:8080/%s?%s", api, json));
        HttpURLConnection connection = getResponseServer(url);
        String response = connectionResponseToString(connection);

        assertEquals(200, connection.getResponseCode());

        System.out.println("URL: " + url.toString());
        return response;
    }

    private HttpURLConnection getResponseServer(URL url) throws Exception {
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;

        return connection;
    }

    private String connectionResponseToString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;

        while ((current = in.readLine()) != null) {
            urlString += current;
        }

        return urlString;
    }
}