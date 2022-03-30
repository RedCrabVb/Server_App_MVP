package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.dao.ResultTestDAO;
import ru.vivt.dataBase.entity.ResultTestEntity;

@Repository
public interface ResultTestRepository extends CrudRepository<ResultTestEntity, Long> {
}
