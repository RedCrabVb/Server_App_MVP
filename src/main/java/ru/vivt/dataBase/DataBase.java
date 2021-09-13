package ru.vivt.dataBase;

import com.google.gson.JsonObject;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public interface DataBase {
    JsonObject lastNews(int number);
    JsonObject generateAccount();
    JsonObject setPersonData(JsonObject jsonPersonData) throws Exception;
    JsonObject getQrCode(String token);
    boolean isActiveToken(String token);
    Map addRequestOnchangePassword(String email);
    JsonObject changePassword(String token);
}