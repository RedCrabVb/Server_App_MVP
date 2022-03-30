package ru.vivt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.ResultTestDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.dto.Answer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping(path = "app")
public class TestResults {
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private ResultTestDAO resultTestDAO;


    @GetMapping("/testTemplate")
    public String resultsOverview(@RequestParam int id, ModelMap model) {
        var test = testDAO.getTestById(id);

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var answers = new ArrayList<Answer>();
        AtomicInteger i = new AtomicInteger();
        questionDAO.getAllQuestionByIdTest(test.getIdTest()).forEach(q -> {
            answers.add(new Answer(i.getAndIncrement(), q.getText(), q.getAnswer(), q.getComment()));
        });

        model.addAttribute("answers", answers);

        return "result";
    }

    @GetMapping("/resultsOverview")
    public String resultsOverview(ModelMap model) {
        var tests = testDAO.getAllTest(15);

        model.addAttribute("tests", tests);

        return "results_overview";
    }

    @GetMapping("/statisticsTest")
    public String statisticsTest(@RequestParam int id, ModelMap model) {
        var test = testDAO.getTestById(id);

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var results = resultTestDAO.getAllResultTestEntity(id,10);

        model.addAttribute("tests", results);

        return "statistics_test";
    }
}
