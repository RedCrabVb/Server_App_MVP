package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.TestEntity;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long> {
}
