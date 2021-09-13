package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.dataBase.DataBase;
import ru.vivt.server.MailSender;

import java.util.Map;

public class ResetPassword implements Command {
    private MailSender mailSender;
    private DataBase dataBase;
    private String url;

    public ResetPassword(MailSender mailSender, DataBase dataBase) {
        this.mailSender = mailSender;
        this.dataBase = dataBase;
//        url =
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
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
