package ru.vivt.dataBase;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.NewsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

public class HibernateSessionFactory
{
    private static SessionFactory sessionFactory = buildSessionFactory();

    protected static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration =  new Configuration().configure();
            configuration.addAnnotatedClass(AccountsEntity.class);
            configuration.addAnnotatedClass(NewsEntity.class);
            configuration.addAnnotatedClass(ResetPasswordEntity.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        catch (Exception e) {
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
