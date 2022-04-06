package ru.vivt;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.vivt")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SpringConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/testCreator").setViewName("testCreator");
//        registry.addViewController("/testAdd").setViewName("testAdd");
//        registry.addViewController("/resultsOverview").setViewName("resultsOverview");
//        registry.addViewController("/testCreator").setViewName("testCreator");
    }

    @Bean
    public MailSender mailSender() {

        return new MailSender(
                "username",
                "password"
        );
    }
}