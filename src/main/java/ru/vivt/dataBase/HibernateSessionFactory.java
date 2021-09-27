package ru.vivt.dataBase;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.NewsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory = buildSessionFactory();

    protected static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(AccountsEntity.class);
            configuration.addAnnotatedClass(NewsEntity.class);
            configuration.addAnnotatedClass(ResetPasswordEntity.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

//            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
