package ru.vivt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.vivt")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SpringConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public MailSender mailSender(@Value("${mail.properties}") String mailProperties) throws IOException {
        Properties property = new Properties();
        property.load(new FileInputStream(mailProperties));

        return new MailSender(
                property.getProperty("usernameEmail"),
                property.getProperty("passwordEmail")
        );
    }
}