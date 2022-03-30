package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.QuestionEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPasswordEntity, Long> {
}
