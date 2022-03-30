package ru.vivt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountsEntity, Long> {
    //    @Override
//    public AccountsEntity getAccountByEmailAndPassword(String email, String password) {
//        Session session = sessionFactory.getSessionFactory().openSession();
//        Query query = session.createQuery(String.format("FROM %s WHERE password = :password AND email = :email", AccountsEntity.class.getName()));
//        AccountsEntity accounts = (AccountsEntity) query.setParameter("password", password).setParameter("email", email).uniqueResult();
//        Hibernate.initialize(accounts);
//        return accounts;
//    }
//
    Optional<AccountsEntity> getAccountByEmailAndPassword(String email, String password);
}
