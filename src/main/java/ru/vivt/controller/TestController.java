package ru.vivt.controller;

import jakarta.transaction.Transactional;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.vivt.dataBase.dto.Accounts;
import ru.vivt.dataBase.dto.Answer;
import ru.vivt.dataBase.dto.ResultTest;
import ru.vivt.dataBase.entity.QuestionEntity;
import ru.vivt.dataBase.entity.ResultTestEntity;
import ru.vivt.dataBase.entity.TestEntity;
import ru.vivt.repository.QuestionRepository;
import ru.vivt.repository.ResultTestRepository;
import ru.vivt.repository.TestRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

//todo: instead of entities use DTO
@Controller
@RequestMapping(path = "app")
public class TestController {
    @FunctionalInterface
    interface GetAllCoincidences {
        List<Object> getAll(String key);
    }

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultTestRepository resultTestDAO;

    @GetMapping("/testCreator")
    public String testCreatorPage() {
        return "test";
    }

    @RequestMapping(value = "/testAdd",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String testAdd(@RequestParam Map<String, String> map, ModelMap model) {
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
                .mapToObj(i -> new Answer(Long.valueOf(i), map.get(listQ.get(i)), map.get(listR.get(i)), map.get(listC.get(i)))).collect(Collectors.toCollection(LinkedList::new));

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

        return "redirect:editTest?id=" + testEntity.getIdTest();
    }


    @GetMapping("/testTemplate")
    public String resultsOverview(@RequestParam Long id, ModelMap model) {
        var test = testRepository.findById(id).orElseThrow();

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var answers = new ArrayList<Answer>();
        questionRepository.findAll().forEach(q -> {
            if (test.getIdTest() == q.getIdTest()) {
                answers.add(new Answer(q.getIdQuestion(), q.getText(), q.getAnswer(), q.getComment()));
            }
        });
        answers.sort(Comparator.comparing(Answer::getId));

        model.addAttribute("partitionAnswers", ListUtils.partition(answers, 2));

        return "result";
    }

    @GetMapping("/resultsOverview")
    public String resultsOverview(ModelMap model) {
        var tests = testRepository.findAll();

        model.addAttribute("tests", tests);

        return "results_overview";
    }

    @GetMapping("/statisticsTest")
    public String statisticsTest(@RequestParam Long id, ModelMap model) {
        var test = testRepository.findById(id).orElseThrow();

        model.addAttribute("testName", test.getTest());
        model.addAttribute("testDescription", test.getDescription());

        var results =
                StreamSupport.stream(resultTestDAO.findAll().spliterator(), false)
                        .filter(v -> v.getIdTest() == id)
                        .collect(Collectors.toList());

        results.sort((o1, o2) -> {
            var time = Integer.parseInt(o1.getTime()) - Integer.parseInt(o2.getTime());
            var answer = Integer.parseInt(o1.getCountWrongAnswer()) - Integer.parseInt(o2.getCountWrongAnswer());
            if (time != 0) {
                return time;
            }

            return answer;
        });


        var listRes = results.stream().map(f -> {
                    var ac = f.getAccountsEntity();
                    return new ResultTest(
                            f.getIdResultTest(),
                            new Accounts(ac.getIdAccount(),
                                    ac.getQrCode(),
                                    ac.getToken(),
                                    ac.getAccountActiveTime(),
                                    ac.getUsername(),
                                    ac.getEmail(),
                                    ac.getPassword()),
                            f.getIdTest(),
                            f.getTime(),
                            f.getCountWrongAnswer(),
                            f.getDate().toLocalDate()
                    );
                })

                .collect(Collectors.groupingBy(f -> f.getDate().toString()));

        var testDate = new ArrayList(listRes
                .keySet()
                .stream()
                .sorted(Comparator.comparing(LocalDate::parse))
                .toList());
        Collections.reverse(testDate);

        model.addAttribute("tests", listRes);
        model.addAttribute("testDate", testDate);

        return "statistics_test";
    }

    @GetMapping("editTest")
    @Transactional
    public String editTest(@RequestParam Long id, ModelMap model) {
        var test = testRepository.findById(id).orElseThrow();
        var list = test.getQuestions().stream().map(x -> new Answer(x.getIdQuestion(), x.getText(), x.getAnswer(), x.getComment())).collect(Collectors.toList());
        list.sort(new Comparator<Answer>() {
            @Override
            public int compare(Answer o1, Answer o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        model.addAttribute("test", test);
        model.addAttribute("questions", list);

        return "edit_test";
    }

    @GetMapping("deleteAnswer")
    public String deleteAnswer(@RequestParam Long idTest, @RequestParam Long idQuestion) {

        questionRepository.deleteById(idQuestion);
        return "redirect:/app/editTest?id=" + idTest;
    }

    @GetMapping("deleteTest")
    public String deleteTest(@RequestParam Long idTest) {

        testRepository.deleteById(idTest);
        return "redirect:/app/resultsOverview";
    }

    @GetMapping("enableRandomSort")
    @Transactional
    public String enableRandomSort(@RequestParam Long idTest) {

        var test = testRepository.findById(idTest).orElseThrow();
        test.setRandomSortQuestion(!test.isRandomSortQuestion());

        return "redirect:/app/resultsOverview";
    }

    @GetMapping("activeTest")
    @Transactional
    public String activeTest(@RequestParam Long idTest) {

        var test = testRepository.findById(idTest).orElseThrow();
        test.setActive(!test.isActive());

        return "redirect:/app/resultsOverview";
    }

    @Transactional
    @GetMapping("editQuestion")
    public String editQuestion(@RequestParam Long idQuestion, @RequestParam Long idTest, ModelMap model) {
        model.addAttribute("question", questionRepository.findById(idQuestion).orElseThrow());
        model.addAttribute("idTest", idTest);
        return "question_editing";
    }


    @PostMapping("editQuestion")
    public String editQuestion(@ModelAttribute("question") QuestionEntity question,
                               @RequestParam Long idTest,
                               ModelMap model) {
        System.out.println(question);
        System.out.println(model);
        questionRepository.save(question);
        model.addAttribute("question", question);
        model.addAttribute("idTest", idTest);
        return "question_editing";
    }

    @PostMapping("addQuestion")
    public String addQuestion(@ModelAttribute("answer") Answer answer, @RequestParam Long idTest) {
        System.out.println(answer);
        var q = new QuestionEntity(answer.getQuestion(),
                answer.getResponse(),
                idTest,
                answer.getComment());
        questionRepository.save(q);
        return "redirect:/app/editTest?id=" + idTest;
    }

    @GetMapping("addQuestion")
    public String addQuestion(@RequestParam Long idTest, Model model) {
        model.addAttribute("answer", new Answer());
        model.addAttribute("idTest", idTest);
        return "add_question";
    }
}
