package ru.vivt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.ResultTestDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.dto.Answer;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResultTestEntity;
import ru.vivt.dataBase.entity.TestEntity;
import ru.vivt.repository.ResultTestRepository;
import ru.vivt.repository.TestRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "api")
public class TestController {
    private final Gson gson = new Gson();

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ResultTestRepository resultTestRepository;

    @GetMapping("/testAll")
    public List<TestEntity> getAllTest() {
        return StreamSupport.stream(testRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //fixme: the user with the necessary skills will simply get all the answers
    @GetMapping("/test")
    public TestEntity test(@RequestParam Long id) {
        return testRepository.findById(id).get();
    }

    @GetMapping("/getHashAnswer")
    public JsonObject getHash(@RequestParam String question, @RequestParam String answer) {
        JsonObject json = new JsonObject();
        json.addProperty("hash", new Answer(1, question, answer, "").toString());
        return json;
    }

    @GetMapping("/saveResultTest")
    public JsonObject saveResultTest(@RequestBody Map<String, String> map) {
        AccountsEntity account = null;
        var resultTest = new ResultTestEntity(account.getIdAccount(),
                Integer.parseInt(map.get("idTest")),
                map.get("time"),
                map.get("countRightAnswer"),
                map.get("jsonAnswer"));
        resultTestRepository.save(resultTest);

        JsonObject jsonStatus = new JsonObject();
        jsonStatus.addProperty("status", true);
        return jsonStatus;
    }
}
