package ru.vivt.dataBase.dao;

import org.hibernate.Session;
import ru.vivt.dataBase.HibernateSessionFactory;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDAOImp implements AccountDAO {
    @Override
    public void addAccounts(AccountsEntity customer) throws SQLException {
        Session session = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public void updateAccounts(AccountsEntity customer) throws SQLException {

    }

    @Override
    public AccountsEntity getCustomerByID(int customer_id) throws SQLException {
        return null;
    }

    @Override
    public Collection getAllAccounts() throws SQLException {
        Session session = null;
        List accountsEntities = new ArrayList<AccountsEntity>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            accountsEntities = session.createCriteria(AccountsEntity.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountsEntities;
    }

    @Override
    public void deleteAccounts(AccountsEntity customer) throws SQLException {

    }
}
