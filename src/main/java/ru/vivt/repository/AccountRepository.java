package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.AccountsEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountsEntity, Long> {
}
