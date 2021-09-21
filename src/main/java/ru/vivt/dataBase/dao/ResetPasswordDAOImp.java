package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.vivt.dataBase.HibernateSessionFactory;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

import java.sql.SQLException;

public class ResetPasswordDAOImp implements ResetPasswordDAO {
    @Override
    public void addResetPassword(ResetPasswordEntity entity) throws SQLException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResetPasswordEntity getResetPasswordByToken(String token) throws SQLException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
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
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
