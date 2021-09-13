package ru.vivt.api;

import com.google.gson.JsonObject;
import ru.vivt.server.MailSender;

import java.util.Map;

public class ResetPassword implements Command {
    private MailSender mailSender;

    public ResetPassword(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        // TODO: 13.09.2021 generator token, add token in table in DB, send mail
        mailSender.sendMessage("test@gmail.com", "test", "test text");
        return null;
    }
}
