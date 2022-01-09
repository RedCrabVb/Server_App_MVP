package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vivt.dataBase.entity.ResultTestEntity;
import ru.vivt.dataBase.entity.TestEntity;

import java.util.List;

@Component
public class ResultTestImp implements ResultTestDAO {
    @Autowired
    private SessionFactory sessionFactory;

    
    @Override
    public void addResultTest(ResultTestEntity customer) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    @Override
    public ResultTestEntity getResultTestEntityById(int id) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s WHERE idResultTest = :idResultTest", ResultTestEntity.class.getName()));
            ResultTestEntity resultTestEntity = (ResultTestEntity) query.setParameter("idResultTest", id).uniqueResult();
            Hibernate.initialize(resultTestEntity);
            return resultTestEntity;
        }
    }

    @Override
    public List<ResultTestEntity> getAllResultTestEntity(int idTest, int maxCount) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s WHERE idTest = :idTest order by idResultTest desc", ResultTestEntity.class.getName()));
            List<ResultTestEntity> resultTestEntity = query.setParameter("idTest", idTest).setMaxResults(maxCount).getResultList();
            Hibernate.initialize(resultTestEntity);
            return resultTestEntity;
        }
    }


}
