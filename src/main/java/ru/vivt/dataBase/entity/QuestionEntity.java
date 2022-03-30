package ru.vivt.dataBase.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;
    @Lob
    private String text;
    @Lob
    private String answer;
    @Lob
    private String comment;
    private Long idTest;

    public QuestionEntity() {

    }

    public QuestionEntity(String text, String answer, Long idTest, String comment) {
        this.text = text;
        this.answer = answer;
        this.idTest = idTest;
        this.comment = comment;
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

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
