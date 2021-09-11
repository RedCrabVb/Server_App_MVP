package ru.vivt.dataBase;

import com.google.gson.JsonObject;

import java.sql.SQLException;

public interface DataBase {
    JsonObject lastNews(int number);
    JsonObject generateAccount();
    JsonObject setPersonData(JsonObject jsonPersonData);
    JsonObject getQrCode(String token);
    boolean isActiveToken(String token);
}