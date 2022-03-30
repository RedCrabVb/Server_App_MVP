package ru.vivt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPasswordEntity, Long> {
    @Query("SELECT r FROM ResetPassword r WHERE r.token = ?1")
    Optional<ResetPasswordEntity> getResetPasswordByToken(String token);
}
