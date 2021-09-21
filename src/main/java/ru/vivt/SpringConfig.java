package ru.vivt;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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

    @Value("${imagePath}") String imgPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
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