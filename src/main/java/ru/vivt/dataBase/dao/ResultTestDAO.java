package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.ResultTestEntity;
import ru.vivt.dataBase.entity.TestEntity;

import java.util.List;

public interface ResultTestDAO {
    void addResultTest(ResultTestEntity customer);
    ResultTestEntity getResultTestEntityById(int id);
    List<ResultTestEntity> getAllResultTestEntity(int id, int maxCount);
}
