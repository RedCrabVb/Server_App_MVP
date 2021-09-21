package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;

import java.sql.SQLException;

@RestController
public class QrCodeController {
    @GetMapping("/api/qrCode")
    public JsonObject getQrCode (@RequestParam String token) throws SQLException {
        JsonObject jsonQrCode = new JsonObject();
        jsonQrCode.addProperty("qrCode", Factory.getInstance().getAccountDAO().getAccountByToken(token).getQrCode());
        return jsonQrCode;
    }

    @GetMapping("/api/getStatusToken")
    public JsonObject getStatusToken(@RequestParam String token) throws SQLException {
        JsonObject json = new JsonObject();
        json.addProperty("result", Factory.getInstance().getAccountDAO().getAccountByToken(token) != null);
        return json;
    }
}
