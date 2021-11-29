package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.NoSuchElementException;

import static ru.vivt.controller.PersonDataController.toSHA1;

@RestController
public class RegistrationController {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AccountDAO accountDAO;
    
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

            accountDAO.addAccounts(new AccountsEntity(qrCode, token, timeActive, "", "", ""));

            JsonObject jsonReg = new JsonObject();
            jsonReg.addProperty("qrCode", qrCode);
            jsonReg.addProperty("token", token);

            logger.info("send qrCode and token " + jsonReg);
            return jsonReg;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "error when server " + e.getMessage());
            logger.info("error:  " + e.getMessage());
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

            String token = accountDAO.getAccountByEmailAndPassword(email, toSHA1(password)).getToken();

            if (token != null) {
                JsonObject jsonReg = new JsonObject();
                jsonReg.addProperty("token", token);
                logger.info("send token: " + token);
                return jsonReg;
            } else {
                throw new NoSuchElementException("not found accounts with this email");
            }
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "error in server (authorization) + " + e.getMessage());
            logger.info("error:  " + e.getMessage());
            return error;
        }
    }
}
