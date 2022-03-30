package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.QuestionEntity;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
}
