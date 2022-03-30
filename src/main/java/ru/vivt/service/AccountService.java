package ru.vivt.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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

@Service
public class AccountService {
    private AccountRepository repository;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String toSHA1(String value) {
        MessageDigest digest;
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

    public AccountsEntity getByToken(String token) {
        return repository.getAccountByToken(token).orElseThrow(() ->
                new IllegalStateException("not found accounts with this token: " + token));
    }

    @Transactional
    public AccountsEntity updateAccount(String token,
                                        String password,
                                        String email,
                                        String username) {
        var accounts = repository.getAccountByToken(token).orElseThrow(() -> {
                    throw new IllegalStateException("not found Accounts");
                }
        );


        String accountsPassword = accounts.getPassword();
        if (password != null) {
            if (!accountsPassword.isEmpty() && !accountsPassword.equals(password)) {
                throw new IllegalStateException("password incorrect");
            }
            accounts.setPassword(password);
        }


        if (email != null) {
            var accountsOnEqualsEmail = repository.getAccountByMail(email);
            if (email.isEmpty() && accountsOnEqualsEmail.isPresent()) {
                throw new IllegalStateException("Mail is already in the database");
            }
            accounts.setEmail(email);
        }

        if (username != null) {
            if (username.isEmpty() || username.length() < 4) {
                throw new IllegalStateException("not valid username");
            }
            accounts.setUsername(username);
        }

        return accounts;
    }
}
