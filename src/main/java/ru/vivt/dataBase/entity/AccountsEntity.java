package ru.vivt.dataBase.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Accounts")
public class AccountsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;
    private String qrCode;
    private String token;
    private LocalDate accountActiveTime;
    private String username;
    private String email;
    private String password;

    public AccountsEntity() {

    }

    public AccountsEntity(String qrCode, String token, LocalDate accountActiveTime, String username, String email, String password) {
        this.qrCode = qrCode;
        this.token = token;
        this.accountActiveTime = accountActiveTime;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getQrCode() {
        return qrCode;
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
}
