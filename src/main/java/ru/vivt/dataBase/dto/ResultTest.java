package ru.vivt.dataBase.dto;

import jakarta.persistence.*;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ResultTest {
    private int idResultTest;
    private Accounts accountsEntity;
    private int idTest;
    private String time;
    private String countWrongAnswer;
    private LocalDate date;

    public ResultTest() {}

    public ResultTest(int idResultTest, Accounts accountsEntity, int idTest, String time, String countWrongAnswer, LocalDate date) {
        this.idResultTest = idResultTest;
        this.accountsEntity = accountsEntity;
        this.idTest = idTest;
        this.time = time;
        this.countWrongAnswer = countWrongAnswer;
        this.date = date;
    }

    public int getIdResultTest() {
        return idResultTest;
    }

    public void setIdResultTest(int idResultTest) {
        this.idResultTest = idResultTest;
    }

    public Accounts getAccountsEntity() {
        return accountsEntity;
    }

    public void setAccountsEntity(Accounts accountsEntity) {
        this.accountsEntity = accountsEntity;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountWrongAnswer() {
        return countWrongAnswer;
    }

    public void setCountWrongAnswer(String countWrongAnswer) {
        this.countWrongAnswer = countWrongAnswer;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateStr() {
        return date.toString();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeFormatString() {
        var timeLong = Long.parseLong(getTime());
        long time = TimeUnit.MILLISECONDS.toMinutes(timeLong);
        long second = TimeUnit.MILLISECONDS.toSeconds(timeLong) % 60;
        var timeString =  time + " мин. " + second + " c.";
        return timeString;
    }

}
