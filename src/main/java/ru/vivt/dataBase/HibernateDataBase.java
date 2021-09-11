package ru.vivt.dataBase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.vivt.dataBase.modelsHibernate.*;
import ru.vivt.dataBase.modelsHibernate.News;

import javax.xml.crypto.Data;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateDataBase implements DataBase {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private SessionFactory sessionFactory;
    private Gson gson;

    public HibernateDataBase() {
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String token = generateNewToken();
        String qrCode = generateNewToken();
        LocalDate timeActive = LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toLocalDate();

        session.save(new Accounts(qrCode, token,
                timeActive,
                "", "", ""));

        transaction.commit();
        session.close();

        JsonObject jsonReg = new JsonObject();
        jsonReg.addProperty("qrCode", qrCode);
        jsonReg.addProperty("token", token);

        return jsonReg;
    }

    @Override
    public JsonObject setPersonData(JsonObject jsonPersonData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Accounts accounts = (Accounts) session
                .createQuery(String.format("FROM %s s WHERE s.token = '%s'", Accounts.class.getName(), jsonPersonData.get("token").getAsString()))
                .uniqueResult();

        accounts.setEmail(jsonPersonData.keySet().contains("email") ? jsonPersonData.get("email").getAsString() : "");
        accounts.setUsername(jsonPersonData.keySet().contains("username") ? jsonPersonData.get("username").getAsString() : "");
        accounts.setPhoneNumber(jsonPersonData.keySet().contains("phoneNumber") ? jsonPersonData.get("phoneNumber").getAsString() : "");

        session.saveOrUpdate(accounts);
        transaction.commit();

        JsonObject jsonOkey = new JsonObject();
        jsonOkey.addProperty("okey", "okey");
        jsonOkey.addProperty("account", gson.toJson(accounts));
        return jsonOkey;
    }

    @Override
    public boolean isActiveToken(String token) {
        try {
            Session session = sessionFactory.openSession();
            Accounts accounts = (Accounts) session.createQuery(String.format("FROM %s s WHERE s.token = '%s'", Accounts.class.getName(), token)).uniqueResult();
            return  accounts != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JsonObject getQrCode(String token) {
        Accounts accounts = (Accounts) sessionFactory
                .openSession()
                .createQuery(String.format("FROM %s s WHERE s.token = '%s'", Accounts.class.getName(), token))
                .uniqueResult();
        JsonObject jsonQrCode = new JsonObject();
        jsonQrCode.addProperty("qrCode", accounts.getQrCode());
        return jsonQrCode;
    }
}
