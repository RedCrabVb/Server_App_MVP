package ru.vivt;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import ru.vivt.dataBase.HibernateSessionFactory;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.AccountDAOImp;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.NewsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;
import ru.vivt.server.MailSender;
import ru.vivt.server.ServerControl;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.persistence.Access;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.vivt")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MailSender mailSender(@Value("${mail.properties}") String mailProperties) throws Exception {
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream(mailProperties);
        property.load(fis);

        return new MailSender(
                property.getProperty("usernameEmail", ""),
                property.getProperty("passwordEmail", "")
        );
    }

    @Bean
    public ServerControl serverControl() {
        return new ServerControl();
    }

    @Bean
    public SessionFactory sessionFactory(@Value("${hibernate.properties}") String hibernateProperties) throws IOException {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.getProperties().load(new FileReader(hibernateProperties));
        configuration.addAnnotatedClass(AccountsEntity.class);
        configuration.addAnnotatedClass(NewsEntity.class);
        configuration.addAnnotatedClass(ResetPasswordEntity.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    @Bean
    public AccountDAO accountDAO(@Autowired SessionFactory sessionFactory) {
        return new AccountDAOImp(sessionFactory);
    }
}