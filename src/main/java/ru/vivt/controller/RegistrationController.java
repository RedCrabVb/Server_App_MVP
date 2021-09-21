package ru.vivt.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.dataBase.DataBase;
import ru.vivt.dataBase.Factory;
import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.util.List;

@RestController
public class RegistrationController {
    @Autowired
    private DataBase dataBase;

    @GetMapping("/api/registration")
    public JsonObject registration () {
        try {
            List<AccountsEntity> customers = (List<AccountsEntity>) Factory.getInstance().getAccountDAO().getAllAccounts();
            customers.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new JsonObject();
    }
}
