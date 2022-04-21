package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    @Value("${news}")
    private String news;

    @GetMapping("/api/news")
    public String getNews () {
        JsonObject json = new JsonObject();
        json.addProperty("News", news);
        return json.toString();
    }
}
