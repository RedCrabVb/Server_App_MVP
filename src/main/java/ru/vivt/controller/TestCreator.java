package ru.vivt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.vivt.dataBase.dto.Answer;
import ru.vivt.dataBase.entity.QuestionEntity;
import ru.vivt.dataBase.entity.TestEntity;
import ru.vivt.repository.QuestionRepository;
import ru.vivt.repository.TestRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Controller
@RequestMapping(path = "app")
public class TestCreator {
    @FunctionalInterface
    interface GetAllCoincidences {
        List<Object> getAll(String key);
    }

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping("/testCreator")
    public String testCreatorPage() {
        return "test";
    }

    @RequestMapping(value = "/testAdd",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String testAdd(@RequestParam  Map<String, String> map, ModelMap model) {
        String testName = map.get("testName");
        String testDescription = map.get("testDescription");

        model.addAttribute("testName", testName);
        model.addAttribute("testDescription", testDescription);

        GetAllCoincidences getAllCoincidences = (String key) ->
                List.of(map.keySet().stream().filter(f -> f.startsWith(key)).toArray());


        var listQ = getAllCoincidences.getAll("nameQ");
        var listR = getAllCoincidences.getAll("nameR");
        var listC = getAllCoincidences.getAll("nameC");

        var answers = IntStream
                .range(0, listQ.size())
                .mapToObj(i -> new Answer(i, map.get(listQ.get(i)), map.get(listR.get(i)), map.get(listC.get(i)))).collect(Collectors.toCollection(LinkedList::new));

        model.addAttribute("answers", answers);

        var testEntity = new TestEntity(testName, testDescription);
        testRepository.save(testEntity);
        answers.forEach(answer -> questionRepository.save(
                        new QuestionEntity(
                                answer.getQuestion(),
                                answer.getResponse(),
                                testEntity.getIdTest(),
                                answer.getComment())
                )
        );

        return "result";
    }


}
