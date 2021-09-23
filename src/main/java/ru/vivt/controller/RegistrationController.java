package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

@RestController
public class RegistrationController {
    private final Log logger = LogFactory.getLog(getClass());

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }



    @GetMapping("/api/registration")
    public JsonObject registration () {
        logger.info("/api/registration");
        try {
            String token = generateNewToken();
            String qrCode = generateNewToken();
            LocalDate timeActive = LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toLocalDate();

            Factory.getInstance().getAccountDAO().addAccounts(new AccountsEntity(qrCode, token, timeActive, "", "", ""));

            JsonObject jsonReg = new JsonObject();
            jsonReg.addProperty("qrCode", qrCode);
            jsonReg.addProperty("token", token);

            return jsonReg;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "error when server");
            logger.error("error registration", e);
            return error;
        }
    }
}
