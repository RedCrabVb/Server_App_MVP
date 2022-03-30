package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.ResetPasswordDAO;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;
import ru.vivt.MailSender;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static ru.vivt.controller.RegistrationController.generateNewToken;

@RestController
@PropertySource("classpath:application.properties")
public class PersonDataController implements InitializingBean {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private AccountDAO<Collection<ArrayList>> accountDAO;

    @Autowired
    private ResetPasswordDAO resetPasswordDAO;

    private String mailText;

    private String mailHref;

    private String mailHeader;

    @Value("${mail.properties}")
    private String mailProperties;

    public PersonDataController() {
    }

    public static String toSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }

    @GetMapping("/api/setPersonDate")
    public JsonObject setPersonData(@RequestParam Map<String, String> params) {
        try {
            AccountsEntity accounts = accountDAO.getAccountByToken(params.get("token"));

            if (accounts == null) {
                throw new Exception("not found Accounts");
            }

            String accountsPassword = accounts.getPassword();
            if (!accountsPassword.isEmpty() && !accountsPassword.equals(toSHA1(params.get("password")))) {
                throw new Exception("Password incorrect");
            }

            String email = params.get("email");
            if (email != null) {
                List<AccountsEntity> accountsOnEqualsEmail = this.accountDAO.getAccountByEmail(email);
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
            jsonError.addProperty("error", e.getMessage());
            return jsonError;
        }
    }

    @GetMapping("/api/resetPassword")
    public JsonObject resetPassword(@RequestParam Map<String, String> params) {
        try {
            if (params.containsKey("email")) {

                String token = generateNewToken();
                String password = generateNewToken().substring(0, 8);
                AccountsEntity accounts = this.accountDAO.getAccountByEmail(params.get("email")).stream().findFirst().orElseThrow();
                LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();
                resetPasswordDAO.addResetPassword(new ResetPasswordEntity(token, password, timeActive, accounts));

                String url = String.format(Optional.of(mailHref).orElseThrow(), token);
                String body = String.format(Optional.of(mailText).orElseThrow(), password, url);

                new Thread(() -> mailSender.sendMessage(params.get("email"), mailHeader, body)).start();

                JsonObject jsonResetPass = new JsonObject();
                jsonResetPass.addProperty("status", "check your email");

                return jsonResetPass;
            } else if (params.containsKey("token")) {
                String token = params.get("token");
                ResetPasswordEntity resetPasswordEntity = resetPasswordDAO.getResetPasswordByToken(token);
                AccountsEntity account = accountDAO.getAccountByToken(resetPasswordEntity.getAccount().getToken());
                account.setPassword(toSHA1(resetPasswordEntity.getTmpPassword()));
                accountDAO.updateAccounts(account);
                resetPasswordDAO.deleteResetPassword(resetPasswordEntity);

                JsonObject jsonResetPass = new JsonObject();
                jsonResetPass.addProperty("status", "reset password");

                return jsonResetPass;
            } else {
                throw new Exception("bad input data");
            }
        } catch (Exception e) {
            JsonObject jsonError = new JsonObject();
            jsonError.addProperty("error", e.getMessage());
            return jsonError;
        }
    }

    @GetMapping("/api/personData")
    public JsonObject personData(@RequestParam String token) {
        try {
            AccountsEntity accountsEntity = accountDAO.getAccountByToken(token);
            JsonObject json = new JsonObject();
            json.addProperty("email", Optional.ofNullable(accountsEntity.getEmail()).orElse(""));
            json.addProperty("username", Optional.ofNullable(accountsEntity.getUsername()).orElse(""));
            json.addProperty("qrCode", accountsEntity.getQrCode());

            return json;
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", e.getMessage());
            return error;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream(mailProperties);
        property.load(fis);

        mailText = property.getProperty("mailTextResetPassword");
        mailHref = property.getProperty("mailHrefResetPassword");
        mailHeader = property.getProperty("mailHeader");
    }
}
