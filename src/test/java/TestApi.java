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
        }
    }

    @AfterEach
    void endTest() throws Exception {
        Thread.sleep(50);//fix bug java, https://bugs.openjdk.java.net/browse/JDK-8214300
    }

    /*@Test
    void serverRegistration() throws Exception {
        String api = "api/registration";

        String name = "testName";
        String surname = "testSurname";
        String patronymic = "testPatronymic";
        String groups = "pks-020";
        String mail = "mail222";
        String password = "asdf";
        String params = String.format("name=%s&surname=%s&patronymic=%s&groups=%s&password=%s&mail=%s",
                name, surname, patronymic, groups, password, mail);
        String paramEnc = URLEncoder.encode(params, StandardCharsets.UTF_8);
        String result = sendInquiry(api, paramEnc);
        try {
            String token = JsonParser.parseString(result).getAsJsonObject().get("token").getAsString();
            this.token = token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Json get: " + result);
    }*/

/*    @Test
    void serverAuthorizationTest() throws Exception {
        String api = "api/authorization";
        String paramEnc = URLEncoder.encode(String.format("login=%s&password=%s", "Mail", "pass"), StandardCharsets.UTF_8);
        String jsonToken = sendInquiry(api, paramEnc);
        this.token = JsonParser.parseString(jsonToken).getAsJsonObject().get("token").getAsString();

        System.out.println("Json get: " + jsonToken);
    }*/

    @Test
    void serverNewsGet() throws Exception {
        String api = "api/news";
        String result = sendInquiry(api, "");
        System.out.println(result);
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