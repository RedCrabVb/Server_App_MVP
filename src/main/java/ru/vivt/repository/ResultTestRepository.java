package ru.vivt.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.ResultTestEntity;

@Repository
public interface ResultTestRepository extends CrudRepository<ResultTestEntity, Long> {
    @Modifying
    @Query("DELETE from ResultTest WHERE id_account = ?1")
    void deletedByIdUser(Long id);
}
