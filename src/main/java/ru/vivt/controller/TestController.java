package ru.vivt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.dao.QuestionDAO;
import ru.vivt.dataBase.dao.TestDAO;
import ru.vivt.dataBase.entity.QuestionEntity;

import java.util.NoSuchElementException;

import static ru.vivt.controller.PersonDataController.toSHA1;

@RestController
public class TestController {
    private final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private QuestionDAO questionDAO;

    @GetMapping("/api/testAll")
    public JsonObject getAllTest() {
        logger.info("/api/testAll");
        JsonObject jsonTest = new JsonObject();

        jsonTest.add("test", gson.toJsonTree(testDAO.getAllTest(-1)));


        return jsonTest;
    }

    @GetMapping("/api/test")
    public JsonObject authorization(@RequestParam int id) {
        logger.info("/api/test");
        JsonObject jsonTest = new JsonObject();

        jsonTest.add("test", gson.toJsonTree(testDAO.getTestById(id)));

        jsonTest.add("question", gson.toJsonTree(questionDAO.getAllQuestionByIdTest(id)));

        return jsonTest;
    }
}
