package ru.vivt.service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.repository.AccountRepository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    private AccountRepository repository;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public AccountsEntity getRepositoryById(long id) {
        return repository.findById(id).get();
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String toSHA1(String value) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "error NoSuchAlgorithmException";
        }
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }

    public AccountsEntity registration() {
        String token = generateNewToken();
        String qrCode = generateNewToken().substring(5);

        LocalDate timeActive = LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toLocalDate();

        var account = new AccountsEntity(qrCode, token, timeActive, "", "", "");
        repository.save(account);

        return account;
    }

    public AccountsEntity authorization(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("email or password empty");
        }

        var account = repository.getAccountByEmailAndPassword(email, toSHA1(password));

        if (account.isEmpty()) {
            throw new IllegalStateException("not found accounts with this email and password: " + email);
        }
        return account.get();
    }
}
