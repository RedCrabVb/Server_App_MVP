package ru.vivt.dataBase.modelsHibernate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Accounts")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;
    private String qrCode;
    private String token;
    private LocalDate accountActiveTime;
    private String username;
    private String email;
    private String phoneNumber;

    public Accounts() {

    }

    public Accounts(String qrCode, String token, LocalDate accountActiveTime, String username, String email, String phoneNumber) {
        this.qrCode = qrCode;
        this.token = token;
        this.accountActiveTime = accountActiveTime;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getQrCode() {
        return qrCode;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
