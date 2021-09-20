package ru.vivt;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vivt.dataBase.DataBase;
import ru.vivt.dataBase.HibernateDataBase;
import ru.vivt.server.MailSender;
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
    public ServerControl serverControl() throws Exception {
        return new ServerControl(logConfig);
    }

    @Bean
    public String imgPath() {
        return imgPath;
    }
}