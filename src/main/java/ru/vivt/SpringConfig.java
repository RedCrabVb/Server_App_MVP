package ru.vivt;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SpringConfig {
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
    public HandlerAPI apiGetNews(@Autowired DataBase dataBase, @Autowired Server server) throws Exception {
        return new HandlerAPI(new GetNews(dataBase), server);
    }

    @Bean
    public HandlerAPI apiRegistration(@Autowired DataBase dataBase, @Autowired Server server) throws Exception {
        return new HandlerAPI(new Registration(dataBase), server);
    }

    @Bean
    public HandlerAPI apiGetQrCode(@Autowired DataBase dataBase, @Autowired Server server) throws Exception {
        return new HandlerAPI(new GetQrCode(dataBase), server);
    }

    @Bean
    public HandlerAPI apiSetPersonDate(@Autowired DataBase dataBase, @Autowired Server server) throws Exception {
        return new HandlerAPI(new SetPersonData(dataBase), server);
    }

    @Bean
    public HandlerAPI apiGetStatusToken(@Autowired DataBase dataBase, @Autowired Server server) throws Exception {
        return new HandlerAPI(new GetStatusToken(dataBase), server);
    }

    @Bean
    public HandlerAPI resetPassword(@Autowired MailSender mailSender, @Autowired Server server, @Autowired DataBase dataBase) {
        return new HandlerAPI(new ResetPassword(mailSender, dataBase), server);
    }

    @Bean
    public String imgPath() {
        return imgPath;
    }
}