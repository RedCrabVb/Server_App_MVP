package ru.vivt.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import ru.vivt.MailSender;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;
import ru.vivt.repository.AccountRepository;
import ru.vivt.repository.ResetPasswordRepository;
import ru.vivt.service.AccountService;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

import static ru.vivt.service.AccountService.generateNewToken;
import static ru.vivt.service.AccountService.toSHA1;


@RestController
public class PersonDataController implements InitializingBean {
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService service;


    private String mailText;

    private String mailHref;

    private String mailHeader;

    @Value("${mail.properties}")
    private String mailProperties;

    public PersonDataController() {
    }

    @PostMapping("/api/setPersonData")
    public AccountsEntity setPersonData(@RequestBody String body,
                                        @RequestParam(required = false) String password,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String username) {
        return service.updateAccount(JsonParser.parseString(body)
                .getAsJsonObject()
                .get("token")
                .getAsString(), toSHA1(password), email, username);
    }

    @PostMapping("/api/resetPassword/email")
    public String resetPasswordEmail(@RequestParam String email) {
        String token = generateNewToken();
        String password = generateNewToken().substring(0, 8);
        AccountsEntity accounts = this.accountRepository.getAccountByMail(email).orElseThrow();
        LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();
        resetPasswordRepository.save(new ResetPasswordEntity(token, password, timeActive, accounts));

        String url = String.format(Optional.of(mailHref).orElseThrow(), token);
        String body = String.format(Optional.of(mailText).orElseThrow(), password, url);

        new Thread(() -> mailSender.sendMessage(email, mailHeader, body)).start();

        JsonObject json = new JsonObject();
        json.addProperty("result", "true");
        return json.toString();
    }

    @GetMapping("/api/resetPassword/token")
    public String resetPasswordToken(@RequestParam String token) {
        ResetPasswordEntity resetPasswordEntity = resetPasswordRepository.getResetPasswordByToken(token).orElseThrow();
        AccountsEntity account = resetPasswordEntity.getAccount();
        account.setPassword(toSHA1(resetPasswordEntity.getTmpPassword()));
        accountRepository.save(account);
        resetPasswordRepository.delete(resetPasswordEntity);

        JsonObject json = new JsonObject();
        json.addProperty("result", "true");
        return json.toString();
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
