package ru.vivt;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addViewController("/resultsOverview").setViewName("resultsOverview");
        registry.addViewController("/testCreator").setViewName("testCreator");
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    @Bean
    public MailSender mailSender() {

        return new MailSender(
                "username",
                "password"
        );
    }

//    @Bean
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
//        configuration.getProperties().load(new FileReader(hibernateProperties));
//        configuration.addAnnotatedClass(AccountsEntity.class);
//        configuration.addAnnotatedClass(NewsEntity.class);
//        configuration.addAnnotatedClass(ResetPasswordEntity.class);
//        configuration.addAnnotatedClass(TestEntity.class);
//        configuration.addAnnotatedClass(QuestionEntity.class);
//        configuration.addAnnotatedClass(ResultTestEntity.class);
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//        return configuration.buildSessionFactory(builder.build());
//    }
//
//    @Bean
//    public AccountDAO<Collection<ArrayList>> accountDAO(@Autowired SessionFactory sessionFactory) {
//        return new AccountDAOImp(sessionFactory);
//    }
}