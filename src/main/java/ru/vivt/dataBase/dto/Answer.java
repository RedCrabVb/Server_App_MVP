package ru.vivt.dataBase.dto;

import java.util.Objects;

public class Answer {
    private Long id;
    private String question;
    private String response;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    private String hash;

    public Answer() {
    }

    public Answer(Long id, String question, String response, String comment) {
        this.question = question;
        this.response = response;
        this.id = id;
        this.comment = comment;
        this.hash = toString();
    }


    @Override
    public String toString() {
        return String.valueOf(response.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(question, answer.question) && Objects.equals(response, answer.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, response);
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
