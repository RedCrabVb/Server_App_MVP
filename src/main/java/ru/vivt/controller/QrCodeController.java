package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.service.AccountService;

import java.time.LocalDate;


@RestController
public class QrCodeController {

    @Autowired
    private AccountService service;

    @GetMapping("/api/qrCode")
    public JsonObject getQrCode(@RequestParam String token) {
        JsonObject json = new JsonObject();
        json.addProperty("qrCode", service.getByToken(token).getQrCode());
        return json;
    }

    @GetMapping("/api/getStatusToken")
    public JsonObject getStatusToken(@RequestParam String token) {
        var account = service.getByToken(token);

        JsonObject json = new JsonObject();
        json.addProperty("result", !account.getAccountActiveTime().isBefore(LocalDate.now()));
        return json;
    }
}
