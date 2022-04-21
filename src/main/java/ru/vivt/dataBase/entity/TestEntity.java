package ru.vivt.dataBase.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTest;
    @Lob
    private String test;
    @Lob
    private String description;
    @OneToMany
    @JoinColumn(name = "idTest")
    private List<QuestionEntity> questions;
    private boolean active = false;
    private boolean randomSortQuestion = false;

    public TestEntity() {

    }

    public TestEntity(String test, String description) {
        this.test = test;
        this.description = description;
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

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRandomSortQuestion() {
        return randomSortQuestion;
    }

    public void setRandomSortQuestion(boolean randomSortQuestion) {
        this.randomSortQuestion = randomSortQuestion;
    }
}

