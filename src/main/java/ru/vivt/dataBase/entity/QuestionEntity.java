package ru.vivt.dataBase.entity;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;
    private String text;
    private String answer;
    private int idTest;

    public QuestionEntity() {

    }

    public QuestionEntity(String text, String answer, int idTest) {
        this.text = text;
        this.answer = answer;
        this.idTest = idTest;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }
}
