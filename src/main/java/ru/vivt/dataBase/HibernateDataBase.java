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
import ru.vivt.server.ServerControl;

import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;

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
        configuration.addAnnotatedClass(ResetPassword.class);
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
    public JsonObject setPersonData(String password, String email, String token, String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Accounts accounts = (Accounts) session
                .createQuery(String.format("FROM %s s WHERE s.token = '%s'", Accounts.class.getName(), token))
                .uniqueResult();

        JsonObject json = new JsonObject();
//        String email, password;
        try {
//            if (!jsonPersonData.has("password")) {
//                throw new Exception("password not set");
//            } else {
//                password = jsonPersonData.get("password").getAsString();
//            }

            if (!accounts.getPassword().isEmpty() && !accounts.getPassword().equals(toSHA1(password))) {
                throw new Exception("Password incorrect");
            }

//            if (jsonPersonData.has("email")) {
//                email = jsonPersonData.get("email").getAsString();
                List<Accounts> accountsOnEqualsEmail  = session.createQuery(String.format("FROM %s s WHERE s.email = '%s'", Accounts.class.getName(), email)).list();
                if (accountsOnEqualsEmail.size() != 0) {
                    throw new Exception("Mail is already in the database");
                }
                accounts.setEmail(email);
//            }

            accounts.setUsername(username);
            accounts.setPassword(toSHA1(password));

            session.saveOrUpdate(accounts);
            transaction.commit();

            json.addProperty("status", true);
            json.addProperty("account", gson.toJson(accounts));
        } catch (Exception e) {
            json.addProperty("status", false);
            json.addProperty("error", e.getMessage());
        }
        return json;
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
    public Map addRequestOnchangePassword(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String token = generateNewToken();
        String password = generateNewToken().substring(0, 8);
        Accounts accounts = (Accounts) session.createQuery(String.format("FROM %s s WHERE s.email = '%s'", Accounts.class.getName(), email)).uniqueResult();
        LocalDate timeActive = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toLocalDate();

        session.save(new ResetPassword(token, password, timeActive, accounts));

        transaction.commit();
        session.close();

        Map<String, String> maps = new HashMap<>();
        maps.put("token", token);
        maps.put("tmpPassword", password);

        return maps;
    }

    @Override
    public JsonObject changePassword(String token) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ResetPassword resetPassword = (ResetPassword) session
                .createQuery(String.format("FROM %s s WHERE s.token = '%s'", ResetPassword.class.getName(), token))
                .uniqueResult();

        JsonObject json = new JsonObject();
        try {
            // TODO: 13.09.2021 chick date
            resetPassword.getAccount().setPassword(toSHA1(resetPassword.getTmpPassword()));

            session.saveOrUpdate(resetPassword);
            session.delete(resetPassword);
            transaction.commit();

            json.addProperty("status", true);
        } catch (Exception e) {
            json.addProperty("status", false);
            json.addProperty("error", e.getMessage());
            ServerControl.LOGGER.log(Level.WARNING, "error in change password", e);
        }
        return json;
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

    private static String toSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }
}
