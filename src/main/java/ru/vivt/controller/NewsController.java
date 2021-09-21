package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    @GetMapping("/api/news")
    public JsonObject getNews () {
        JsonObject json = new JsonObject();
        json.addProperty("News", "https://habr.com/ru/news/");
        return json;
    }
}
