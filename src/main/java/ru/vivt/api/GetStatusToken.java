package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.dataBase.DataBase;

import java.util.Map;

public class GetStatusToken implements Command {
    private DataBase dataBase;

    public GetStatusToken(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("result", dataBase.isActiveToken(params.get("token")));
        return json;
    }
}
