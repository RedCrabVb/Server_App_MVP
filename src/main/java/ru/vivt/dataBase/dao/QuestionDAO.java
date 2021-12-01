package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.QuestionEntity;

public interface QuestionDAO {
    void addQuestion(QuestionEntity customer);
    QuestionEntity getQuestionById(int id);
}
