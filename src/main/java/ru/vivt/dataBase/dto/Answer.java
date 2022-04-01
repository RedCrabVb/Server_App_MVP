package ru.vivt.dataBase.dto;

import java.util.Objects;

public class Answer {
    private Long id;
    private final String question;
    private final String response;
    private final String comment;

    public Answer(Long id, String question, String response, String comment) {
        this.question = question;
        this.response = response;
        this.id = id;
        this.comment = comment;
    }

    public String getResponse() {
        return response;
    }


    public String getQuestion() {
        return question;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getComment() {
        return comment;
    }

}
