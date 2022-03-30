package ru.vivt.dataBase.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTest;
    private String test;
    private String description;
    @OneToMany
    @JoinColumn(name = "idTest")
    private List<QuestionEntity> questions;

    public TestEntity() {

    }

    public TestEntity(String test, String description) {
        this.test = test;
        this.description = description;
    }


    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
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

    public List<QuestionEntity> getQuestions() {
        return questions;
    }
}

