package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
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
import java.util.*;

import static ru.vivt.controller.RegistrationController.generateNewToken;

@RestController
public class PersonDataController {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MailSender mailSender;

    public static String toSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }

    @GetMapping("/api/setPersonDate")
    public JsonObject setPersonData(@RequestParam Map<String, String> params) throws Exception {
        logger.info("params: " + params.toString());
        try {
            AccountDAO accountDAO = Factory.getInstance().getAccountDAO();
            AccountsEntity accounts = accountDAO.getAccountByToken(params.get("token"));

            if (!accounts.getPassword().isEmpty() && !accounts.getPassword().equals(toSHA1(params.get("password")))) {
                throw new Exception("Password incorrect");
            }

            String email = params.get("email");
            if (email != null) {
                List<AccountsEntity> accountsOnEqualsEmail = Factory.getInstance().getAccountDAO().getAccountByEmail(email);
                if (accountsOnEqualsEmail.size() != 0) {
                    throw new Exception("Mail is already in the database");
                }
                accounts.setEmail(email);
            }

            accounts.setUsername(params.get("username"));
            accounts.setPassword(toSHA1(params.get("password")));

            accountDAO.updateAccounts(accounts);

            JsonObject jsonStatus = new JsonObject();
            jsonStatus.addProperty("status", true);
            return jsonStatus;
        } catch (Exception e) {
            JsonObject jsonError = new JsonObject();
            jsonError.addProperty("error", "bad input data or error server");
            logger.error("error", e);
            return jsonError;
        }
    }

    @GetMapping("/api/resetPassword")
    public JsonObject resetPassword(@RequestParam Map<String, String> params) {
        logger.info("params" + params.toString());
        try {
            if (params.containsKey("email")) {

                String token = generateNewToken();
                String password = generateNewToken().substring(0, 8);
                AccountsEntity accounts = Factory.getInstance().getAccountDAO().getAccountByToken(params.get("token"));
                LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();
                Factory.getInstance().getResetPasswordDAO().addResetPassword(new ResetPasswordEntity(token, password, timeActive, accounts));

                Map<String, String> maps = new HashMap<>();
                maps.put("token", token);
                maps.put("tmpPassword", password);

                String url = "http://localhost:8080/api/resetPassword?token=" + token;
                String body = String.format("Your new password %s, <a href=\"%s\">click here</a> to reset your old password", password, url);

                new Thread(() -> mailSender.sendMessage(params.get("email"), "Password reset", body)).start();

                JsonObject jsonResetPass = new JsonObject();
                jsonResetPass.addProperty("status", "check your email");

                return jsonResetPass;
            } else if (params.containsKey("token")) {
                String token = params.get("token");
                ResetPasswordEntity resetPasswordEntity = Factory.getInstance().getResetPasswordDAO().getResetPasswordByToken(token);
                resetPasswordEntity.getAccount().setPassword(toSHA1(resetPasswordEntity.getTmpPassword()));
                Factory.getInstance().getResetPasswordDAO().deleteResetPassword(resetPasswordEntity);

                JsonObject jsonResetPass = new JsonObject();
                jsonResetPass.addProperty("status", "reset password");

                return jsonResetPass;
            } else {
                throw new Exception("bad input data");
            }
        } catch (Exception e) {
            JsonObject jsonError = new JsonObject();
            jsonError.addProperty("error", "bad input data or error server");
            logger.error("error", e);
            return jsonError;
        }
    }

    @GetMapping("/api/personData")
    public JsonObject personData(@RequestParam String token) {
        logger.info("/api/personData?token=" + token);
        try {
            AccountsEntity accountsEntity = Factory.getInstance().getAccountDAO().getAccountByToken(token);
            JsonObject jsonQrCode = new JsonObject();
            jsonQrCode.addProperty("email", Optional.ofNullable(accountsEntity.getEmail()).orElse(""));
            jsonQrCode.addProperty("username", Optional.ofNullable(accountsEntity.getUsername()).orElse(""));
            jsonQrCode.addProperty("qrCode", accountsEntity.getQrCode());
            return jsonQrCode;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "qr get from DB error or input bad");
            logger.error("error", e);
            return error;
        }
    }
}
