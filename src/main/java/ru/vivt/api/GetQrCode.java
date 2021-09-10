package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.dataBase.DataBase;

import java.util.Map;

public class GetQrCode implements Command {
    private DataBase dataBase;

    public GetQrCode(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.getQrCode(params.get("token"));
    }
}
