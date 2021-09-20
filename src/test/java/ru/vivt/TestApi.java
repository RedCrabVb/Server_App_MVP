package ru.vivt;

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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vivt.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//import static org.junit.Assert.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApi {
    private final String apiNews = "api/news";
    private final String apiQrCode = "api/qrCode";
    private final String apiPersonData = "api/setPersonDate";
    private final String apiRegistration = "api/registration";
    private final String apiImgNews = "src/img";
    private final String apiStatusToken = "api/getStatusToken";
    private final String apiResetPassword = "api/resetPassword";


    @Autowired public PropertySourceDataTestUser propertySourceDataTestUser;
    @Autowired public Server server;


    private static String token;
    private static String passwordNewAccount = "newPassword";

    @Test
    @Order(1)
    public void serverRegistration() throws Exception {
        server.start();
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
        String result = sendInquiry(apiPersonData, String.format("token=%s&email=emailTest&password=%s",  token, passwordNewAccount));
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
        System.out.println(result);
    }

    @Test
    @Order(5)
    public void getStatusToken() throws Exception {
        assertEquals(true, JsonParser.parseString(sendInquiry(apiStatusToken, String.format("token=%s",  token)))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    @Test
    @Order(6)
    public void getQrCodeError() throws Exception {
       assertThrows(java.io.IOException.class,
                () -> {sendInquiry(apiQrCode, "token=" + token + "error");});
    }


    @Test
    @Order(7)
    public void setPersonDataForCurrentAccount() throws Exception {
        String result = sendInquiry(apiPersonData, String.format("token=%s&password=%s",
                token,
                passwordNewAccount));
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
        System.out.println(result);
    }

    @Test
    @Order(8)
    public void getStatusTokenError() throws Exception {
        assertEquals(false, JsonParser.parseString(sendInquiry(apiStatusToken, String.format("token=%serror",  token)))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    @Test
    @Order(9)
    public void resetPassword() throws Exception {
        String result = sendInquiry(apiResetPassword, String.format("email=%s", propertySourceDataTestUser.getEmailUserTest()));
        System.out.println(result);
    }


    private static String sendInquiry(String api, String json) throws Exception {
        json = json.replace("+", "%20"); // fix space encoder
        URL url = new URL(String.format("http://localhost:8080/%s?%s", api, json));
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