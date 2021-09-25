package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

import static ru.vivt.controller.PersonDataController.toSHA1;

@RestController
public class RegistrationController {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final Log logger = LogFactory.getLog(getClass());

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


    @GetMapping("/api/registration")
    public JsonObject registration() {
        logger.info("/api/registration");
        try {
            String token = generateNewToken();
            String qrCode = generateNewToken().substring(5);

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

    @GetMapping("/api/authorization")
    public JsonObject authorization(@RequestParam String email, @RequestParam String password) {
        logger.info("/api/authorization");
        try {
            if (email.isEmpty() || password.isEmpty()) {
                throw new Exception("email or password empty");
            }

            String token = Factory.getInstance().getAccountDAO().getAccountByEmailAndPassword(email, toSHA1(password)).getToken();

            JsonObject jsonReg = new JsonObject();
            jsonReg.addProperty("token", token);

            return jsonReg;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "error in server (authorization)");
            logger.error("error registration", e);
            return error;
        }
    }
}
