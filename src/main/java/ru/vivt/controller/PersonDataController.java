package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.DataBase;
import ru.vivt.server.MailSender;

import java.util.Map;

@RestController
public class PersonDataController {
    @Autowired
    private DataBase dataBase;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/api/setPersonDate")
    public JsonObject setPersonData (@RequestParam Map<String,String> params) throws Exception { //@RequestParam Map<String,String> params
        return dataBase.setPersonData(params.get("password"), params.get("email"), params.get("token"), params.containsKey("username") ? params.get("username") : ""); //@RequestParam String token, @RequestParam String email, @RequestParam String password
    }

    @GetMapping("/api/resetPassword")
    public JsonObject resetPassword(@RequestParam Map<String,String> params) {
        // TODO: 13.09.2021 generator token, add token in table in DB, send mail
        if (params.containsKey("email")) {
            String email = params.get("email");
            Map<String, String> map = dataBase.addRequestOnchangePassword(email);
            String url = "http://localhost:8080/api/resetPassword?token=" + map.get("token");
            String body = String.format("Your new password %s, <a href=\"%s\">click here</a> to reset your old password", map.get("tmpPassword"), url);
            mailSender.sendMessage(email, "Password reset", body);
            JsonObject json = new JsonObject();
            json.addProperty("status", "check you email");
            return json;
        } else if (params.containsKey("token")) {
            String token = params.get("token");
            return dataBase.changePassword(token);
        }
        return null;
    }
}
