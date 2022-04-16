package ru.vivt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vivt.dataBase.dto.Answer;
import ru.vivt.dataBase.dto.Test;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResultTestEntity;
import ru.vivt.dataBase.entity.TestEntity;
import ru.vivt.repository.AccountRepository;
import ru.vivt.repository.ResultTestRepository;
import ru.vivt.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api")
public class TestRestController {
    private final Gson gson = new Gson();

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ResultTestRepository resultTestRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/testAll")
    public List<Test> getAllTest() {
        List<Test> list = new ArrayList<>();
        testRepository.findAll().forEach(t -> list.add(new Test(t.getIdTest(), t.getTest(), t.getDescription(), t.isActive())));
        return list;
    }

    @GetMapping("/test")
    @Transactional
    public Test test(@RequestParam Long id) {
        TestEntity testEntity = testRepository.findById(id).orElseThrow();
        Test test = new Test(testEntity.getIdTest(), testEntity.getTest(), testEntity.getDescription(), testEntity.isActive());

        List<Answer> list = test.getAnswerList();
        testEntity.getQuestions().forEach(
                q -> list.add(new Answer(q.getIdQuestion(), q.getText(), q.getAnswer(), q.getComment()))
        );
        test.setAnswerList(list);
        return  test;
    }

    @GetMapping("/getHashAnswer")
    public JsonObject getHash(@RequestParam String question, @RequestParam String answer) {
        JsonObject json = new JsonObject();
        json.addProperty("hash", new Answer(1L, question, answer, "").toString());
        return json;
    }

    @GetMapping("/saveResultTest")
    public String saveResultTest(@RequestParam Map<String, String> map) {
        AccountsEntity account = accountRepository.getAccountByToken(map.get("token")).orElseThrow();
        var resultTest = new ResultTestEntity(account,
                Integer.parseInt(map.get("idTest")),
                map.get("time"),
                map.get("countRightAnswer"));
        resultTestRepository.save(resultTest);

        JsonObject jsonStatus = new JsonObject();
        jsonStatus.addProperty("status", true);
        return jsonStatus.toString();
    }
}
