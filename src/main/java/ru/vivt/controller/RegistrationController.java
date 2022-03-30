package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.service.AccountService;

@RestController
public class RegistrationController {

    @Autowired
    private AccountService repository;


    @PostMapping("/api/registration")
    public AccountsEntity registration() {
        return repository.registration();
    }

    @PostMapping("/api/authorization")
    public AccountsEntity authorization(@RequestParam String email, @RequestParam String password) {
        return repository.authorization(email, password);
    }
}
