package ru.vivt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountsEntity, Long> {

    @Query("SELECT a FROM Accounts a WHERE s.password = ?2 AND s.email = ?1")
    Optional<AccountsEntity> getAccountByEmailAndPassword(String email, String password);

    @Query("SELECT a FROM Accounts a WHERE s.token = ?1")
    Optional<AccountsEntity> getAccountByToken(String token);

    @Query("SELECT a FROM Accounts a WHERE s.email = ?1")
    Optional<AccountsEntity> getAccountByMail(String email);
}
