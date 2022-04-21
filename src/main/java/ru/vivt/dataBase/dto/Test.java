package ru.vivt.dataBase.dto;

import jakarta.persistence.*;
import ru.vivt.dataBase.entity.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private Long idTest;
    private String test;
    private String description;
    private boolean active = false;
    private List<Answer> answerList = new ArrayList<>();
    private boolean randomSortQuestion = false;

    public Test() {

    }

    public Test(Long idTest, String test, String description, boolean active, boolean randomSortQuestion) {
        this.idTest = idTest;
        this.test = test;
        this.description = description;
        this.active = active;
        this.randomSortQuestion = randomSortQuestion;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public boolean isRandomSortQuestion() {
        return randomSortQuestion;
    }

    public void setRandomSortQuestion(boolean randomSortQuestion) {
        this.randomSortQuestion = randomSortQuestion;
    }
}