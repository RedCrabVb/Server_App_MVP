package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.DataBase;

@RestController
public class QrCodeController {
    @Autowired
    private DataBase dataBase;

    @GetMapping("/api/qrCode")
    public JsonObject getQrCode (@RequestParam String token) {
        return dataBase.getQrCode(token);
    }

    @GetMapping("/api/getStatusToken")
    public JsonObject getStatusToken(@RequestParam String token) {
        JsonObject json = new JsonObject();
        json.addProperty("result", dataBase.isActiveToken(token));
        return json;
    }
}
