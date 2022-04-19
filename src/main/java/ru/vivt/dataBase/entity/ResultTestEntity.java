package ru.vivt.dataBase.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity(name = "ResultTest")
@Table(name = "ResultTest")
public class ResultTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResultTest;
    @ManyToOne
    @JoinColumn(name = "idAccount")
    private AccountsEntity accountsEntity;
    private int idTest;
    private String time;
    private String countWrongAnswer;
    private LocalDateTime date;

    public ResultTestEntity() {

    }

    public ResultTestEntity(AccountsEntity accountsEntity, int idTest, String time, String countRightAnswer) {
        this.accountsEntity = accountsEntity;
        this.idTest = idTest;
        this.time = time;
        this.countWrongAnswer = countRightAnswer;
    }

    public String getTime() {
        return time;
    }

    public String getCountWrongAnswer() {
        return countWrongAnswer;
    }


    public int getIdTest() {
        return idTest;
    }

    public AccountsEntity getAccountsEntity() {
        return accountsEntity;
    }

    public int getIdResultTest() {
        return idResultTest;
    }

    public void setIdResultTest(int idResultTest) {
        this.idResultTest = idResultTest;
    }

    public String getTimeFormatString() {
        var timeLong = Long.parseLong(getTime());
        long time = TimeUnit.MILLISECONDS.toMinutes(timeLong);
        long second = TimeUnit.MILLISECONDS.toSeconds(timeLong) % 60;
        var timeString =  time + " мин. " + second + " c.";
        return timeString;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

