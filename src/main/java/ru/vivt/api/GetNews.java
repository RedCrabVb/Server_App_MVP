package ru.vivt.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.vivt.dataBase.DataBase;
import ru.vivt.server.HandlerAPI;

import java.util.Map;

public class GetNews implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        JsonObject jsonNews = new JsonObject();
        jsonNews.addProperty("title", "hello world");
        return jsonNews;
    }
}
