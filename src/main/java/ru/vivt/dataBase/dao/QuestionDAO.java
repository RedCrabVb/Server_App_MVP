package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.QuestionEntity;

import java.util.List;

public interface QuestionDAO {
    void addQuestion(QuestionEntity customer);
    QuestionEntity getQuestionById(int id);
    List<QuestionEntity> getAllQuestionByIdTest(int id);
}
