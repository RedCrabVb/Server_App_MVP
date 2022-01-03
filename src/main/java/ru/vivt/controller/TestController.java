package ru.vivt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.entity.Answer;

@RestController
public class TestController {
    private final Gson gson = new Gson();
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private QuestionDAO questionDAO;

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
}
