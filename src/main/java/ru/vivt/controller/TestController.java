package ru.vivt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.ResultTestDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.entity.Answer;
import ru.vivt.dataBase.entity.ResultTestEntity;

import java.sql.SQLException;
import java.util.Map;

@RestController
public class TestController {
    private final Gson gson = new Gson();
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private ResultTestDAO resultTestDAO;

    @GetMapping("/api/testAll")
    public JsonObject getAllTest() {
        JsonObject jsonTest = new JsonObject();
        jsonTest.add("test", gson.toJsonTree(testDAO.getAllTest(5)));
        return jsonTest;
    }

    //fixme: the user with the necessary skills will simply get all the answers
    @GetMapping("/api/test")
    public JsonObject test(@RequestParam int id) {
        JsonObject jsonTest = new JsonObject();
        jsonTest.add("test", gson.toJsonTree(testDAO.getTestById(id)));
        jsonTest.add("question", gson.toJsonTree(questionDAO.getAllQuestionByIdTest(id)));
        return jsonTest;
    }

    @GetMapping("/api/getHashAnswer")
    public JsonObject getHash(@RequestParam String question, @RequestParam String answer) {
        JsonObject json = new JsonObject();
        json.addProperty("hash", new Answer(1, question, answer, "").toString());
        return json;
    }

    @GetMapping("/api/saveResultTest")
    public JsonObject saveResultTest(@RequestParam Map<String, String> map) {
        var account = accountDAO.getAccountByToken(map.get("token"));
        var resultTest = new ResultTestEntity(account.getIdAccount(),
                Integer.parseInt(map.get("idTest")),
                map.get("time"),
                map.get("countRightAnswer"),
                map.get("jsonAnswer"));
        resultTestDAO.addResultTest(resultTest);

        JsonObject jsonStatus = new JsonObject();
        jsonStatus.addProperty("status", true);
        return jsonStatus;
    }
}
