package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vivt.dataBase.entity.TestEntity;

import java.util.Comparator;
import java.util.List;

@Component
public class TestDAOImp implements TestDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTest(TestEntity customer) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    @Override
    public TestEntity getTestById(int id) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s WHERE idTest = :idTest", TestEntity.class.getName()));
            TestEntity question = (TestEntity) query.setParameter("idTest", id).uniqueResult();
            Hibernate.initialize(question);
            return question;
        }
    }

    @Override
    public List<TestEntity> getAllTest(int maxCount) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s order by idTest desc", TestEntity.class.getName()));
            List<TestEntity> question = query.setMaxResults(maxCount).getResultList();
            Hibernate.initialize(question);
            return question;
        }
    }
}
