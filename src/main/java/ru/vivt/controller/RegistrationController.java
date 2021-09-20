package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.DataBase;

@RestController
public class RegistrationController {
    @Autowired
    private DataBase dataBase;

    @GetMapping("/api/registration")
    public JsonObject registration () {
        return dataBase.generateAccount();
    }
}
