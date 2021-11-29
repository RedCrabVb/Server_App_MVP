package ru.vivt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestCreator {
    @GetMapping("/testCreator")
    public String testCreatorPage(Model model) {
        model.addAttribute("message", "Hello world");
        return "test";
    }
}
