package ru.vivt.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.entity.Answer;
import ru.vivt.dataBase.entity.QuestionEntity;
import ru.vivt.dataBase.entity.TestEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TestCreator {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Value("${admin.token}")
    private String token;


    @GetMapping("/testCreator")
    public String testCreatorPage(Model model) {
//        model.addAttribute("message", token);
        return "test";
    }

    @GetMapping("/testAdd")
    public String testAdd(@RequestParam Map<String, String> map, ModelMap model) {
        logger.info("test add, parameter: " + map.toString());

        if (!map.get("token").equals(token)) {
            model.addAttribute("message", "Токен не верный");
            return "error";
        }

        String testName = map.get("testName");
        String testDescription = map.get("testDescription");

        model.addAttribute("testName", testName);
        model.addAttribute("testDescription", testDescription);

        var listQ = List.of(map.keySet().stream().filter(f -> f.startsWith("nameQ")).toArray());
        var listR = List.of(map.keySet().stream().filter(f -> f.startsWith("nameR")).toArray());

        var answers = IntStream
                .range(0, listQ.size())
                .mapToObj(i -> new Answer(i, map.get(listQ.get(i)), map.get(listR.get(i)))).collect(Collectors.toCollection(LinkedList::new));

        answers.forEach(s -> logger.info("answer test: " + s));

        model.addAttribute("answers", answers);

        var testEntity = new TestEntity(testName, testDescription);
        testDAO.addTest(testEntity);
        answers.forEach(answer -> questionDAO.addQuestion(
                        new QuestionEntity(
                                answer.getQuestion(),
                                answer.getResponse(),
                                testEntity.getIdTest())
                )
        );

        return "result";
    }

    //TODO: send answer android and check right this
}
