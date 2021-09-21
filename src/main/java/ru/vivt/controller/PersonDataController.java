package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;
import ru.vivt.server.MailSender;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.vivt.controller.RegistrationController.generateNewToken;

@RestController
public class PersonDataController {
    @Autowired
    private MailSender mailSender;

    @GetMapping("/api/setPersonDate")
    public JsonObject setPersonData (@RequestParam Map<String,String> params) throws Exception {
        AccountDAO accountDAO =   Factory.getInstance().getAccountDAO();
        AccountsEntity accounts = accountDAO.getAccountByToken(params.get("token"));
        JsonObject json = new JsonObject();
        try {
            if (!accounts.getPassword().isEmpty() && !accounts.getPassword().equals(toSHA1(params.get("password")))) {
                throw new Exception("Password incorrect");
            }

            List<AccountsEntity> accountsOnEqualsEmail = new ArrayList<>();
            if (accountsOnEqualsEmail.size() != 0) {
                throw new Exception("Mail is already in the database");
            }
            accounts.setEmail(params.get("email"));

            accounts.setUsername(params.get("username"));
            accounts.setPassword(toSHA1(params.get("password")));

            accountDAO.updateAccounts(accounts);

            json.addProperty("status", true);
        } catch (Exception e) {
            json.addProperty("status", false);
            json.addProperty("error", e.getMessage());
        }
        return json;

    }

    @GetMapping("/api/resetPassword")
    public JsonObject resetPassword(@RequestParam Map<String,String> params) {
        if (params.containsKey("email")) {
            String email = params.get("email");
            Map<String, String> map = new HashMap<>();

            String token = generateNewToken();
            String password = generateNewToken().substring(0, 8);
            AccountsEntity accounts = null;
            try {
                accounts = Factory.getInstance().getAccountDAO().getAccountByToken(params.get("token"));
                LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();
                Factory.getInstance().getResetPasswordDAO().addResetPassword(new ResetPasswordEntity(token, password, timeActive, accounts));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            Map<String, String> maps = new HashMap<>();
            maps.put("token", token);
            maps.put("tmpPassword", password);

            String url = "http://localhost:8080/api/resetPassword?token=" + token;
            String body = String.format("Your new password %s, <a href=\"%s\">click here</a> to reset your old password", password, url);
            mailSender.sendMessage(email, "Password reset", body);
            JsonObject json = new JsonObject();
            json.addProperty("status", "check you email");
            return json;
        } else if (params.containsKey("token")) {
            String token = params.get("token");
            try {
                ResetPasswordEntity resetPasswordEntity = Factory.getInstance().getResetPasswordDAO().getResetPasswordByToken(token);
                resetPasswordEntity.getAccount().setPassword(toSHA1(resetPasswordEntity.getTmpPassword()));
                Factory.getInstance().getResetPasswordDAO().deleteResetPassword(resetPasswordEntity);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

            return null;
        }
        return null;
    }

    private static String toSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }
}
