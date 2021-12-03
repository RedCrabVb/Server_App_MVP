package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.TestEntity;

import java.util.List;

public interface TestDAO {
    void addTest(TestEntity customer);
    TestEntity getTestById(int id);
    List<TestEntity> getAllTest(int maxCount);
}
