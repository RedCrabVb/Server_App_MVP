package ru.vivt.dataBase.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Accounts {
    private Long idAccount;
    private String qrCode;
    private String token;
    private LocalDate accountActiveTime;
    private String username;
    private String email;
    private String password;

    public Accounts() {}

    public Accounts(Long idAccount, String qrCode, String token, LocalDate accountActiveTime, String username, String email, String password) {
        this.idAccount = idAccount;
        this.qrCode = qrCode;
        this.token = token;
        this.accountActiveTime = accountActiveTime;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getAccountActiveTime() {
        return accountActiveTime;
    }

    public void setAccountActiveTime(LocalDate accountActiveTime) {
        this.accountActiveTime = accountActiveTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
