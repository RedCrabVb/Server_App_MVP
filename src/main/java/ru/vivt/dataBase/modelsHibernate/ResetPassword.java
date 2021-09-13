package ru.vivt.dataBase.modelsHibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ResetPassword")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResetPassword;
    private String token;
    private String tmpPassword;
    private LocalDate dateActiveToken;
    @ManyToOne
    @JoinColumn(name = "idAccount")
    private Accounts account;

    public ResetPassword() {

    }

    /**
     *
     * @param token
     * @param tmpPassword
     * @param dateActiveToken
     * @param account
     */
    public ResetPassword(String token, String tmpPassword, LocalDate dateActiveToken, Accounts account) {
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

    public Accounts getAccount() {
        return account;
    }
}
