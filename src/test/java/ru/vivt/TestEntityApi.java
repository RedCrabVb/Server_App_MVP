package ru.vivt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ContextConfiguration
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEntityApi {
    private static final String serverName = "localhost";//"servermvp.ru:49379";

    private final String apiNews = "api/news";
    private final String apiQrCode = "api/qrCode";
    private final String apiPersonData = "api/setPersonDate";
    private final String apiRegistration = "api/registration";
    private final String apiStatusToken = "api/getStatusToken";
    private final String apiResetPassword = "api/resetPassword";
    private final String apiAuthorization = "api/authorization";
    private final String apiPersonDataGet = "api/personData";

    @Autowired public PropertySourceDataTestUser propertySourceDataTestUser;


    private static String token;
    private static String passwordNewAccount = "newPassword";
    private static String emailTest = "emailTest";

    @Test
    @Order(1)
    public void serverRegistration() throws Exception {
        SpringApplication.run(Main.class);

        String result = sendInquiry(apiRegistration, "");
        token = JsonParser.parseString(result).getAsJsonObject().get("token").getAsString();
        System.out.println(result);
    }

    @Test
    @Order(2)
    public void serverNewsGet() throws Exception {
        String result = sendInquiry(apiNews, "");
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("News").toString().isEmpty());
        System.out.println(result);
    }

    @Test
    @Order(3)
    public void getQrCode() throws Exception {
        String result = sendInquiry(apiQrCode, "token=" + token);
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("qrCode").getAsString().isEmpty());
        System.out.println(result);
    }

    @Test
    @Order(4)
    public void setPersonData() throws Exception {
        String result = sendInquiry(apiPersonData, String.format("token=%s&email=%s&password=%s",  token, emailTest, passwordNewAccount));
        System.out.println(result);
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
    }

    @Test
    @Order(5)
    public void getStatusToken() throws Exception {
        assertEquals(true, JsonParser.parseString(sendInquiry(apiStatusToken, String.format("token=%s",  token)))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    @Test
    public void authorization() throws Exception {
        String result = sendInquiry(apiAuthorization, String.format("email=%s&password=%s",  emailTest, passwordNewAccount));
        JsonParser.parseString(result).getAsJsonObject().get("token");
        System.out.println(result);
    }

    @Test
    @Order(6)
    public void setPersonDataForCurrentAccount() throws Exception {
        String result = sendInquiry(apiPersonData, String.format("token=%s&password=%s",
                token,
                passwordNewAccount));
        System.out.println(result);
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
    }

    @Test
    public void testPersonDataGet() throws Exception {
        String result = sendInquiry(apiPersonDataGet, String.format("token=%s",  token));
        System.out.println(result);
        assertTrue(JsonParser.parseString(result).getAsJsonObject().has("email"));
    }

    @Test
    public void getStatusTokenError() throws Exception {
        JsonObject json = JsonParser.parseString(sendInquiry(apiStatusToken, String.format("token=%serror",  token)))
                .getAsJsonObject();
        assertEquals(false, json.get("result").getAsBoolean());
        System.out.println(json);
    }

    @Test
    @Order(8)
    public void resetPassword() throws Exception {
        String result = sendInquiry(apiResetPassword, String.format("email=%s", propertySourceDataTestUser.getEmailUserTest()));
        System.out.println(result);
    }

    @Test
    public void getQrCodeError() throws Exception {
        JsonObject jsonError = JsonParser.parseString(sendInquiry(apiQrCode, "token=" + token + "error")).getAsJsonObject();
        assertTrue(jsonError.has("error"));
        System.out.println(jsonError);
    }

    @Test
    public void setPersonDataError() throws Exception {
        JsonObject jsonError = JsonParser.parseString(sendInquiry(apiPersonData, "token=" + token + "error")).getAsJsonObject();
        assertTrue(jsonError.has("error"));
        System.out.println(jsonError);
    }

    @Test
    public void resetPasswordError() throws Exception {
        JsonObject jsonError = JsonParser.parseString(sendInquiry(apiResetPassword, String.format("var=%s", "user"))).getAsJsonObject();
        assertTrue(jsonError.has("error"));
        System.out.println(jsonError);
    }


    private static String sendInquiry(String api, String json) throws Exception {
        json = json.replace("+", "%20"); // fix space encoder
        URL url = new URL(String.format("http://" + serverName +":8080/%s?%s", api, json));
        HttpURLConnection connection = getResponseServer(url);
        String response = connectionResponseToString(connection);

        assertEquals(200, connection.getResponseCode());

        System.out.println("URL: " + url.toString());
        return response;
    }

    private static HttpURLConnection getResponseServer(URL url) throws Exception {
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;

        return connection;
    }

    private static String connectionResponseToString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;

        while ((current = in.readLine()) != null) {
            urlString += current;
        }

        return urlString;
    }
}