package ru.vivt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import ru.vivt.server.ServerControl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.logging.Level;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);

        ServerControl.LOGGER.log(Level.INFO, "Server start " + new Date().toString());
    }
}