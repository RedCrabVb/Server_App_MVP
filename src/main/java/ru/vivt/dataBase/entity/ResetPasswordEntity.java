package ru.vivt.dataBase.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ResetPassword")
public class ResetPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResetPassword;
    private String token;
    private String tmpPassword;
    private LocalDate dateActiveToken;
    @ManyToOne
    @JoinColumn(name = "idAccount")
    private AccountsEntity account;

    public ResetPasswordEntity() {

    }

    /**
     *
     * @param token
     * @param tmpPassword
     * @param dateActiveToken
     * @param account
     */
    public ResetPasswordEntity(String token, String tmpPassword, LocalDate dateActiveToken, AccountsEntity account) {
        this.token = token;
        this.tmpPassword = tmpPassword;
        this.dateActiveToken = dateActiveToken;
        this.account = account;
    }

    public int getIdResetPassword() {
        return idResetPassword;
    }

    public String getToken() {
        return token;
    }

    public String getTmpPassword() {
        return tmpPassword;
    }

    public LocalDate getDateActiveToken() {
        return dateActiveToken;
    }

    public AccountsEntity getAccount() {
        return account;
    }
}
