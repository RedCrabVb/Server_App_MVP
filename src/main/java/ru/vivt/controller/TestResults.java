package ru.vivt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vivt.dataBase.dto.Answer;
import ru.vivt.repository.QuestionRepository;
import ru.vivt.repository.ResultTestRepository;
import ru.vivt.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.collections4.ListUtils;

@Controller
@RequestMapping(path = "app")
public class TestResults {
    @Autowired
    private TestRepository testDAO;
    @Autowired
    private QuestionRepository questionDAO;
    @Autowired
    private ResultTestRepository resultTestDAO;


    @GetMapping("/testTemplate")
    public String resultsOverview(@RequestParam Long id, ModelMap model) {
        var test = testDAO.findById(id).orElseThrow();

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var answers = new ArrayList<Answer>();
        AtomicInteger i = new AtomicInteger();
        questionDAO.findAll().forEach(q -> {
            if (test.getIdTest() == q.getIdTest()) {
                answers.add(new Answer(i.getAndIncrement(), q.getText(), q.getAnswer(), q.getComment()));
            }
        });

        model.addAttribute("partitionAnswers", ListUtils.partition(answers, 2));

        return "result";
    }

    @GetMapping("/resultsOverview")
    public String resultsOverview(ModelMap model) {
        var tests = testDAO.findAll();

        model.addAttribute("tests", tests);

        return "results_overview";
    }

    @GetMapping("/statisticsTest")
    public String statisticsTest(@RequestParam Long id, ModelMap model) {
        var test = testDAO.findById(id).orElseThrow();

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var results =
                StreamSupport.stream(resultTestDAO.findAll().spliterator(), false)
                        .filter(v -> v.getIdTest() == id)
                        .collect(Collectors.toList());

        model.addAttribute("tests", results);

        return "statistics_test";
    }
}
