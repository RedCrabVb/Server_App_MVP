package ru.vivt;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.AccountDAOImp;
import ru.vivt.dataBase.entity.*;
import ru.vivt.server.MailSender;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Configuration
@ComponentScan("com.vivt")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SpringConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/testCreator").setViewName("testCreator");
        registry.addViewController("/testAdd").setViewName("testAdd");
    }

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
    public SessionFactory sessionFactory(@Value("${hibernate.properties}") String hibernateProperties) throws IOException {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.getProperties().load(new FileReader(hibernateProperties));
        configuration.addAnnotatedClass(AccountsEntity.class);
        configuration.addAnnotatedClass(NewsEntity.class);
        configuration.addAnnotatedClass(ResetPasswordEntity.class);
        configuration.addAnnotatedClass(TestEntity.class);
        configuration.addAnnotatedClass(QuestionEntity.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    @Bean
    public AccountDAO<Collection<ArrayList>> accountDAO(@Autowired SessionFactory sessionFactory) {
        return new AccountDAOImp(sessionFactory);
    }
}