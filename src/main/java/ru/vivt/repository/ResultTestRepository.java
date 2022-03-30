package ru.vivt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResultTestEntity;

import java.util.Optional;

@Repository
public interface ResultTestRepository extends CrudRepository<ResultTestEntity, Long> {
}
