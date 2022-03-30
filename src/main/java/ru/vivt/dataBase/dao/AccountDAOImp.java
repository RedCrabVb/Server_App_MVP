package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AccountDAOImp implements AccountDAO {
//    private final SessionFactory sessionFactory;
//
//    public AccountDAOImp(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void addAccounts(AccountsEntity entity) {
//        try (Session session = sessionFactory.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.save(entity);
//            session.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public void updateAccounts(AccountsEntity entity) throws SQLException {
//        try (Session session = sessionFactory.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.update(entity);
//            session.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public AccountsEntity getAccountByID(int customer_id) throws SQLException {
//        Session session = sessionFactory.getSessionFactory().openSession();
//        AccountsEntity customer = session.load(AccountsEntity.class, customer_id);
//        Hibernate.initialize(customer);
//        return customer;
//    }
//
//    @Override
//    public AccountsEntity getAccountByToken(String token) {
//        Session session = sessionFactory.getSessionFactory().openSession();
//        Query query = session.createQuery(String.format("FROM %s WHERE token = :token", AccountsEntity.class.getName()));
//        AccountsEntity accounts = (AccountsEntity) query.setParameter("token", token).uniqueResult();
//        Hibernate.initialize(accounts);
//        return accounts;
//    }
//
//    @Override
//    public AccountsEntity getAccountByEmailAndPassword(String email, String password) {
//        Session session = sessionFactory.getSessionFactory().openSession();
//        Query query = session.createQuery(String.format("FROM %s WHERE password = :password AND email = :email", AccountsEntity.class.getName()));
//        AccountsEntity accounts = (AccountsEntity) query.setParameter("password", password).setParameter("email", email).uniqueResult();
//        Hibernate.initialize(accounts);
//        return accounts;
//    }
//
//    @Override
//    public List getAccountByEmail(String email) {
//        Session session = sessionFactory.getSessionFactory().openSession();
//        Query query = session.createQuery(String.format("FROM %s WHERE email = :email", AccountsEntity.class.getName()));
//        List<AccountsEntity> accounts = query.setParameter("email", email).list();
//        Hibernate.initialize(accounts);
//        return accounts;
//    }
//
//    @Override
//     public Collection<ArrayList> getAllAccounts() throws SQLException {
//        try (Session session = sessionFactory.getSessionFactory().openSession()) {
//            return session.createCriteria(AccountsEntity.class).list();
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    @Override
//    public void deleteAccounts(AccountsEntity customer) throws SQLException {
//        try (Session session = sessionFactory.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.delete(customer);
//            session.getTransaction().commit();
//        }
//    }
}
