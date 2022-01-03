package ru.vivt.controller;

import com.google.gson.JsonObject;
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

@FunctionalInterface
interface GetAllCoincidences {
    List<Object> getAll(String key);
}

@Controller
public class TestCreator {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private QuestionDAO questionDAO;




    @GetMapping("/testCreator")
    public String testCreatorPage() {
        return "test";
    }

    @GetMapping("/testAdd")
    public String testAdd(@RequestParam Map<String, String> map, ModelMap model) {
        logger.info("test add, parameter: " + map.toString());

        String testName = map.get("testName");
        String testDescription = map.get("testDescription");

        model.addAttribute("testName", testName);
        model.addAttribute("testDescription", testDescription);

        GetAllCoincidences getAllCoincidences = (String key) ->
                List.of(map.keySet().stream().filter(f -> f.startsWith(key)).toArray());


        var listQ = getAllCoincidences.getAll("nameQ");
        var listR = getAllCoincidences.getAll("nameR");
        var listC = getAllCoincidences.getAll("nameС");

        var answers = IntStream
                .range(0, listQ.size())
                .mapToObj(i -> new Answer(i, map.get(listQ.get(i)), map.get(listR.get(i)), map.get(listC.get(i)))).collect(Collectors.toCollection(LinkedList::new));

        answers.forEach(s -> logger.info("answer test: " + s));

        model.addAttribute("answers", answers);

        var testEntity = new TestEntity(testName, testDescription);
        testDAO.addTest(testEntity);
        answers.forEach(answer -> questionDAO.addQuestion(
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
