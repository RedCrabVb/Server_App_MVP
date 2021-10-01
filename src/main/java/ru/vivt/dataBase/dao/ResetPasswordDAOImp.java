package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import ru.vivt.dataBase.HibernateSessionFactory;
import org.springframework.stereotype.Component;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

import java.sql.SQLException;

@Component
public class ResetPasswordDAOImp implements ResetPasswordDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addResetPassword(ResetPasswordEntity entity) throws SQLException {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResetPasswordEntity getResetPasswordByToken(String token) throws SQLException {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s WHERE token = :token", ResetPasswordEntity.class.getName()));
            ResetPasswordEntity resetPasswordEntity = (ResetPasswordEntity) query.setParameter("token", token).uniqueResult();
            Hibernate.initialize(resetPasswordEntity);
            return resetPasswordEntity;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteResetPassword(ResetPasswordEntity entity) throws SQLException {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
