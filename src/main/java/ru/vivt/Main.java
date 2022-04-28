package ru.vivt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        String version = "2.3.1-SNAPSHOT";
        System.out.println("   _____ ______ _______      ________ _____    __  ____      _______  \n" +
                "  / ____|  ____|  __ \\ \\    / /  ____|  __ \\  |  \\/  \\ \\    / /  __ \\ \n" +
                " | (___ | |__  | |__) \\ \\  / /| |__  | |__) | | \\  / |\\ \\  / /| |__) |\n" +
                "  \\___ \\|  __| |  _  / \\ \\/ / |  __| |  _  /  | |\\/| | \\ \\/ / |  ___/ \n" +
                "  ____) | |____| | \\ \\  \\  /  | |____| | \\ \\  | |  | |  \\  /  | |     \n" +
                " |_____/|______|_|  \\_\\  \\/   |______|_|  \\_\\ |_|  |_|   \\/   |_|     \n" +
                "                                          ______                      \n" +
                "                                         |______|            " + version);
    }
}