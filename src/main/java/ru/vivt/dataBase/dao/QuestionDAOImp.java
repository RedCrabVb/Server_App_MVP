package ru.vivt.dataBase.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vivt.dataBase.entity.QuestionEntity;


@Component
public class QuestionDAOImp implements QuestionDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addQuestion(QuestionEntity customer) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    @Override
    public QuestionEntity getQuestionById(int id) {
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery(String.format("FROM %s WHERE idQuestion = :id", QuestionEntity.class.getName()));
            QuestionEntity question = (QuestionEntity) query.setParameter("idQuestion", id).uniqueResult();
            Hibernate.initialize(question);
            return question;
        }
    }

}
