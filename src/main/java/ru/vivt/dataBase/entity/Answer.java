package ru.vivt.dataBase.entity;

import java.util.Objects;

public class Answer {
    private int id;
    private String question;
    private String response;

    public Answer(int id, String question, String response) {
        this.question = question;
        this.response = response;
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return "answer-" + id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Answer@" + question.hashCode() + response.hashCode();
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


}
