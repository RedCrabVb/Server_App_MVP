package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class NewsController {
    @Value("${news}")
    private String news;

    @GetMapping("/api/news")
    public JsonObject getNews () {
        JsonObject json = new JsonObject();
        json.addProperty("News", news);
        return json;
    }
}
