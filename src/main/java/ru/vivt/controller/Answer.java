package ru.vivt.controller;

import java.util.Objects;

public class Answer {
    private String question;
    private String response;

    public Answer(String question, String response) {
        this.question = question;
        this.response = response;
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

    @Override
    public String toString() {
        return "Answer{" +
                "question='" + question + '\'' +
                ", response='" + response + '\'' +
                '}';
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
