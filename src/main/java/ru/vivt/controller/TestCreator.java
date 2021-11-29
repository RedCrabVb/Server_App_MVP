package ru.vivt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TestCreator {
    @GetMapping("/testCreator")
    public String testCreatorPage(@RequestParam String token, Model model) {
        model.addAttribute("message", token);
        return "test";
    }

    @GetMapping("/testAdd")
    public String testAdd(@RequestParam Map<String,String> allRequestParams, ModelMap model) {
        System.out.println(allRequestParams.toString());
        System.out.println(allRequestParams.keySet().size());
        return "result";
    }
}
