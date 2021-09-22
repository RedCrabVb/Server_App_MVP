package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;

import java.sql.SQLException;

@RestController
public class QrCodeController {
    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/api/qrCode")
    public JsonObject getQrCode (@RequestParam String token) {
        logger.info("/api/qrCode?token=" + token);
        try {
            JsonObject jsonQrCode = new JsonObject();
            jsonQrCode.addProperty("qrCode", Factory.getInstance().getAccountDAO().getAccountByToken(token).getQrCode());
            return jsonQrCode;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "qr get from DB error or input bad");
            return error;
        }
    }

    @GetMapping("/api/getStatusToken")
    public JsonObject getStatusToken(@RequestParam String token) {
        logger.info("/api/qrCode?token=" + token);
        try {
            // TODO: 21.09.2021 time check
            JsonObject json = new JsonObject();
            json.addProperty("result", Factory.getInstance().getAccountDAO().getAccountByToken(token) != null);
            return json;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "token get from DB error or input bad");
            return error;
        }
    }
}
