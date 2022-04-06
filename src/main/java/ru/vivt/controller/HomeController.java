package ru.vivt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vivt.repository.AccountRepository;


@Controller
@RequestMapping(path = "app")
public class HomeController {

    @GetMapping("home")
    public String menu() {
        return "home";
    }

    @GetMapping("help")
    public String help() {
        return "help";
    }

}
