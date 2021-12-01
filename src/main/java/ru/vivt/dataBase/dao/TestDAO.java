package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.TestEntity;

public interface TestDAO {
    void addTest(TestEntity customer);
    TestEntity getTestById(int id);
}
