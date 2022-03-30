package ru.vivt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "app")
public class HomeController {
    @GetMapping("home")
    public String menu() {
        return "home";
    }


}
