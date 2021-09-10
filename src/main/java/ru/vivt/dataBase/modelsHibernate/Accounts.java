package ru.vivt.dataBase.modelsHibernate;

import javax.persistence.*;

@Entity
@Table(name = "Account")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;
    private String qrCode;
    private String token;
    private int accountActiveTime;
    private String username;
    private String email;
    private String phoneNumber;
}
