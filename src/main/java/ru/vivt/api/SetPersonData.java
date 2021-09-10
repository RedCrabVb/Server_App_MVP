package ru.vivt.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.vivt.dataBase.DataBase;

import java.util.Map;

public class SetPersonData implements Command {
    private DataBase dataBase;
    private Gson gson;

    public SetPersonData(DataBase dataBase) {
        this.dataBase = dataBase;
        this.gson = new Gson();
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.setPersonData(JsonParser.parseString(gson.toJson(params)).getAsJsonObject());
    }
}
