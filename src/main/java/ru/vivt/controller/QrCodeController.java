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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static ru.vivt.controller.RegistrationController.generateNewToken;

@RestController
public class QrCodeController {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AccountDAO accountDAO;

    @GetMapping("/api/qrCode")
    public JsonObject getQrCode(@RequestParam String token) {
        logger.info("/api/qrCode params: token=" + token);
        try {
            AccountsEntity accountsEntity = accountDAO.getAccountByToken(token);

            if (accountsEntity == null) {
                throw new Exception("Not found accounts");
            }

            JsonObject jsonQrCode = new JsonObject();
            jsonQrCode.addProperty("qrCode", accountsEntity.getQrCode());
            return jsonQrCode;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "qr get from DB error or input bad " + e.getMessage());
            logger.info("error: " + e.getMessage());
            return error;
        }
    }

    /**
     * Will return the status token, if the token is out of date, update it
     *
     * @param token
     * @return Json
     */
    @GetMapping("/api/getStatusToken")
    public JsonObject getStatusToken(@RequestParam String token) {
        logger.info("/api/getStatusToken?token=" + token);
        try {
            AccountsEntity account = accountDAO.getAccountByToken(token);
            boolean statusToken = account != null;

            if (statusToken && account.getAccountActiveTime().isBefore(LocalDate.now())) {
                account.setToken(generateNewToken());
                account.setAccountActiveTime(LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toLocalDate());
                accountDAO.updateAccounts(account);
                statusToken = false;
            }

            JsonObject json = new JsonObject();
            json.addProperty("result", statusToken);
            return json;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "token get from DB error or input bad " + e.getMessage());
            logger.info("error: " + e.getMessage());
            return error;
        }
    }
}
