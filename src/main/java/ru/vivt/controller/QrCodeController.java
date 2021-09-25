package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.Factory;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static ru.vivt.controller.RegistrationController.generateNewToken;

@RestController
public class QrCodeController {
    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/api/qrCode")
    public JsonObject getQrCode(@RequestParam String token) {
        logger.info("/api/qrCode?token=" + token);
        try {
            JsonObject jsonQrCode = new JsonObject();
            jsonQrCode.addProperty("qrCode", Factory.getInstance().getAccountDAO().getAccountByToken(token).getQrCode());
            return jsonQrCode;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "qr get from DB error or input bad");
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
            AccountsEntity account = Factory.getInstance().getAccountDAO().getAccountByToken(token);
            boolean statusToken = account != null;

            if (account != null) {
                if (account.getAccountActiveTime().isBefore(LocalDate.now())) {
                    account.setToken(generateNewToken());
                    account.setAccountActiveTime(LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toLocalDate());
                    Factory.getInstance().getAccountDAO().updateAccounts(account);
                    statusToken = false;
                }
            }

            JsonObject json = new JsonObject();
            json.addProperty("result", statusToken);
            return json;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "token get from DB error or input bad");
            return error;
        }
    }
}
