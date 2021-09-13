package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.dataBase.DataBase;
import ru.vivt.server.MailSender;

import java.util.Map;

public class ResetPassword implements Command {
    private MailSender mailSender;
    private DataBase dataBase;

    public ResetPassword(MailSender mailSender, DataBase dataBase) {
        this.mailSender = mailSender;
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        // TODO: 13.09.2021 generator token, add token in table in DB, send mail
        String email = params.get("email");
        mailSender.sendMessage(email, "password reset", "test text + " + dataBase.addRequestOnchangePassword(email));
        JsonObject json = new JsonObject();
        json.addProperty("status", "check you email");
        return json;
    }
}
