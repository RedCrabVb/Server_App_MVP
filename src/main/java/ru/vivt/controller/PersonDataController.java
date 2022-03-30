package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import ru.vivt.MailSender;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.service.AccountService;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@RestController
@PropertySource("classpath:application.properties")
public class PersonDataController implements InitializingBean {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private AccountService service;


    private String mailText;

    private String mailHref;

    private String mailHeader;

    @Value("${mail.properties}")
    private String mailProperties;

    public PersonDataController() {
    }

    @PostMapping("/api/setPersonData2")
    public AccountsEntity setPersonData2(@RequestBody String token,
                                         @RequestBody(required = false) String password,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String username) {
        return service.updateAccount(token, password, email, username);
    }


//    @GetMapping("/api/resetPassword")
//    public JsonObject resetPassword(@RequestParam Map<String, String> params) {
//        try {
//            if (params.containsKey("email")) {
//
//                String token = generateNewToken();
//                String password = generateNewToken().substring(0, 8);
//                AccountsEntity accounts = this.accountDAO.getAccountByEmail(params.get("email")).stream().findFirst().orElseThrow();
//                LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();
//                resetPasswordDAO.addResetPassword(new ResetPasswordEntity(token, password, timeActive, accounts));
//
//                String url = String.format(Optional.of(mailHref).orElseThrow(), token);
//                String body = String.format(Optional.of(mailText).orElseThrow(), password, url);
//
//                new Thread(() -> mailSender.sendMessage(params.get("email"), mailHeader, body)).start();
//
//                JsonObject jsonResetPass = new JsonObject();
//                jsonResetPass.addProperty("status", "check your email");
//
//                return jsonResetPass;
//            } else if (params.containsKey("token")) {
//                String token = params.get("token");
//                ResetPasswordEntity resetPasswordEntity = resetPasswordDAO.getResetPasswordByToken(token);
//                AccountsEntity account = accountDAO.getAccountByToken(resetPasswordEntity.getAccount().getToken());
//                account.setPassword(toSHA1(resetPasswordEntity.getTmpPassword()));
//                accountDAO.updateAccounts(account);
//                resetPasswordDAO.deleteResetPassword(resetPasswordEntity);
//
//                JsonObject jsonResetPass = new JsonObject();
//                jsonResetPass.addProperty("status", "reset password");
//
//                return jsonResetPass;
//            } else {
//                throw new Exception("bad input data");
//            }
//        } catch (Exception e) {
//            JsonObject jsonError = new JsonObject();
//            jsonError.addProperty("error", e.getMessage());
//            return jsonError;
//        }
//    }

    @GetMapping("/api/personData")
    public AccountsEntity personData(@RequestParam String token) {
        return service.getByToken(token);
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
