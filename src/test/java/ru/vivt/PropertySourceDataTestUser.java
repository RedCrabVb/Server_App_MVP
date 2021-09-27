package ru.vivt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:test-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class PropertySourceDataTestUser {
    @Value("${user.test.Token}")  private String tokenCurrentAccount;
    @Value("${user.test.Email}") private String emailUserTest;
    @Value("${user.test.password}") private String userTestPassword;

    public String getTokenCurrentAccount() {
        return tokenCurrentAccount;
    }

    public String getUserTestPassword() {
        return userTestPassword;
    }

    public String getEmailUserTest() {
        return emailUserTest;
    }
}
