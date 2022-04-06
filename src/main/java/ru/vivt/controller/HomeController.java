package ru.vivt.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vivt.util.PrintStreamDouble;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Controller
@RequestMapping(path = "app")
public class HomeController implements InitializingBean {
    @Value("${server.log.clear}")
    private String clearLog;

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public HomeController() {
        PrintStreamDouble ps = new PrintStreamDouble(baos, System.out);
        System.setOut(ps);
    }

    @GetMapping("home")
    public String menu() {
        return "home";
    }

    @GetMapping("help")
    public String help(Model model) {
        Properties properties = null;
        var listProp = new ArrayList<String>();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {

            properties = new Properties();

            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.forEach((k, v) -> listProp.add(k + "=" + v));
        model.addAttribute("properties", listProp);
        return "help";
    }

    @GetMapping("log")
    public String log(Model model) {
        var log = baos.toString().replace("\n", "<br>");//warning XSS
        model.addAttribute("log", log);
        return "log";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            var clearLogInt = Integer.parseInt(clearLog) * 1000;
            while (true) {
                baos.reset();
                try {
                    Thread.sleep(clearLogInt);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
