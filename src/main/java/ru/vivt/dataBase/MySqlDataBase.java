package ru.vivt.dataBase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.vivt.dataBase.modelsHibernate.*;
import ru.vivt.dataBase.modelsHibernate.News;

import java.util.List;
import java.util.stream.Collectors;

public class MySqlDataBase implements DataBase {
    private SessionFactory sessionFactory;
    private Gson gson;

    public MySqlDataBase() {
        Configuration configuration =  new Configuration().configure();
        configuration.addAnnotatedClass(Accounts.class);
        configuration.addAnnotatedClass(News.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        gson = new Gson();
    }

    @Override
    public JsonObject lastNews(int number) {
        List<News> listNews = sessionFactory.openSession()
                .createQuery(String.format("FROM %s f ORDER BY f.id DESC", News.class.getName()))
                .setMaxResults(number)
                .list();
        JsonObject jsonNews = new JsonObject();
        jsonNews.add("News", JsonParser.parseString(gson.toJson(listNews)));
        return jsonNews;
    }

    @Override
    public JsonObject generateAccount() {
        return null;
    }

    @Override
    public JsonObject setPersonData(JsonObject jsonPersonData) {
        return null;
    }

    @Override
    public JsonObject getQrCode(int id) {
        return null;
    }
}
