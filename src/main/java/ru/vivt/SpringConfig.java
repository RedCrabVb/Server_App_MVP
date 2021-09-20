package ru.vivt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vivt.api.*;
import ru.vivt.dataBase.DataBase;
import ru.vivt.dataBase.HibernateDataBase;
import ru.vivt.server.HandlerAPI;
import ru.vivt.server.MailSender;
import ru.vivt.server.Server;
import ru.vivt.server.ServerControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.vivt")
@PropertySource("classpath:config.properties")
@PropertySource("classpath:mail.properties")
public class SpringConfig implements WebMvcConfigurer {
    @Value("${serverPort}") int serverPort;
    @Value("${logConfPath}") String logConfig;

    @Value("${typeBase}") String typeBase;
    @Value("${imagePath}") String imgPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataBase dataBase() throws Exception {
        if (typeBase.equals("mysql")) {
            return new HibernateDataBase();
        } else {
            throw new Exception("Config error, type base = " + typeBase);
        }
    }

    @Bean
    public MailSender mailSender(@Value("${usernameEmail}") String usernameEmail, @Value("${passwordEmail}") String passwordEmail) {
        return new MailSender(usernameEmail, passwordEmail);
    }

    @Bean
    public Server server(@Autowired DataBase dataBase) throws Exception {
        return new Server(serverPort, dataBase);
    }

    @Bean
    public ServerControl serverControl(@Autowired Server server) throws Exception {
        return new ServerControl(logConfig, server);
    }

    @Bean
    public String imgPath() {
        return imgPath;
    }

/*
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
*/

}