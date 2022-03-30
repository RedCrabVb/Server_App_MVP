package ru.vivt.dataBase.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "ResultTest")
public class ResultTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResultTest;
    private int idAccount;
    private int idTest;
    private String time;
    private String countRightAnswer;
    private String jsonAnswer;

    public ResultTestEntity() {

    }

    public ResultTestEntity(int idAccount, int idTest, String time, String countRightAnswer, String jsonAnswer) {
        this.idAccount = idAccount;
        this.idTest = idTest;
        this.time = time;
        this.countRightAnswer = countRightAnswer;
        this.jsonAnswer = jsonAnswer;
    }

    public String getTime() {
        return time;
    }

    public String getCountRightAnswer() {
        return countRightAnswer;
    }

    public String getJsonAnswer() {
        return jsonAnswer;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public int getIdTest() {
        return idTest;
    }
}

