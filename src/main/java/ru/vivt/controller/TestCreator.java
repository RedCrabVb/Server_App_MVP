package ru.vivt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class TestCreator {
    @GetMapping("/testCreator")
    public String testCreatorPage(@RequestParam String token, Model model) {
        model.addAttribute("message", token);
        return "test";
    }

    @GetMapping("/testAdd")
    public String testAdd(@RequestParam Map<String,String> map, ModelMap model) {
        System.out.println(map.toString());
        System.out.println(map.keySet().size());

        model.addAttribute("testName", map.get("testName"));
        model.addAttribute("testDescription", map.get("testDescription"));

        var listQ = List.of(map.keySet().stream().filter(f -> f.startsWith("nameQ")).toArray());
        var listR = List.of(map.keySet().stream().filter(f -> f.startsWith("nameR")).toArray());

        var answers = List.of(IntStream
                .range(0, listQ.size())
                .mapToObj(i -> new Answer(map.get(listQ.get(i)), map.get(listR.get(i)))).toArray());

        answers.forEach(s -> System.out.println(s));

        model.addAttribute("answers", answers);

        return "result";
    }
}
