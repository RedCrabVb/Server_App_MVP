package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.dataBase.DataBase;

import java.util.Map;

public class Registration implements Command {
    private DataBase dataBase;

    public Registration(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.generateAccount();
    }
}
