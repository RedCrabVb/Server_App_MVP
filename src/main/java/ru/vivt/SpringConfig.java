package ru.vivt;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vivt.server.MailSender;
import ru.vivt.server.ServerControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.vivt")
@PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class SpringConfig implements WebMvcConfigurer {

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
        return new ServerControl();
    }
}