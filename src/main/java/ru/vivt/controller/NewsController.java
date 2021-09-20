package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.DataBase;

@RestController
//@RequestMapping("/api/news")
public class NewsController {
    @Autowired private DataBase dataBase;

    @GetMapping("/api/news")
    public JsonObject getNews () {
        System.out.println("sd");
        JsonObject json = dataBase.lastNews(10);
        return json;
    }
}
