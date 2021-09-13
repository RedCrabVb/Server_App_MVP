package ru.vivt;

import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vivt.server.Server;
import ru.vivt.server.ServerControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;


//@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApi {
    @Autowired public PropertySourceDataTestUser propertySourceDataTestUser;
    @Autowired public Server server;

    private static String token;

    @BeforeTestClass
    public void serverStart() throws Exception {
        server.start();
        serverRegistration();
    }

    @BeforeEach
    public void test() throws Exception {
        if (token == null) {
            serverRegistration();
        }
    }

    @AfterEach
    public void endTest() throws Exception {
        Thread.sleep(50);//fix bug java, https://bugs.openjdk.java.net/browse/JDK-8214300
    }

    @Test
    public void serverNewsGet() throws Exception {
        String api = "api/news";
        String result = sendInquiry(api, "");
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("News").toString().isEmpty());
        System.out.println(result);
    }

    @Test
    public void serverRegistration() throws Exception {
        String api = "api/registration";
        String result = sendInquiry(api, "");
        token = JsonParser.parseString(result).getAsJsonObject().get("token").getAsString();
        System.out.println(result);
    }

    @Test
    public void getQrCode() throws Exception {
        String api = "api/qrCode";
        String result = sendInquiry(api, "token=" + token);
        assertEquals(true, !JsonParser.parseString(result).getAsJsonObject().get("qrCode").getAsString().isEmpty());
        System.out.println(result);
    }

    @Test
    public void setPersonData() throws Exception {
        String api = "api/setPersonDate";
        String result = sendInquiry(api, String.format("token=%s&email=emailTest&password=newPassword",  token));
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
        System.out.println(result);
    }

    @Test
    public void getStatusToken() throws Exception {
        String api = "api/getStatusToken";
        assertEquals(true, JsonParser.parseString(sendInquiry(api, String.format("token=%s",  propertySourceDataTestUser.getTokenCurrentAccount())))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    @Test
    public void getQrCodeError() throws Exception {
        server.start();
        String api = "api/qrCode";
        java.io.IOException thrown = assertThrows(java.io.IOException.class,
                () -> {sendInquiry(api, "token=" + propertySourceDataTestUser.getUserTestPassword() + "error");});
    }


    @Test
    public void setPersonDataForCurrentAccount() throws Exception {
        String api = "api/setPersonDate";
        String result = sendInquiry(api, String.format("token=%s&password=%s",
                propertySourceDataTestUser.getTokenCurrentAccount(),
                propertySourceDataTestUser.getUserTestPassword()));
        assertTrue(JsonParser.parseString(result).getAsJsonObject().get("status").getAsBoolean());
        System.out.println(result);
    }

    @Test
    public void getStatusTokenError() throws Exception {
        String api = "api/getStatusToken";
        assertEquals(false, JsonParser.parseString(sendInquiry(api, String.format("token=%serror",  token)))
                .getAsJsonObject().get("result").getAsBoolean());
    }

    @Test
    public void resetPassword() throws Exception {
        String api = "api/resetPassword";
        String result = sendInquiry(api, String.format("email=%s", propertySourceDataTestUser.getEmailUserTest()));
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