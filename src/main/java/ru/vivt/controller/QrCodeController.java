package ru.vivt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vivt.service.AccountService;

import java.time.LocalDate;


@RestController
public class QrCodeController {

    @Autowired
    private AccountService service;

    @GetMapping("/api/qrCode")
    public String getQrCode(@RequestParam String token) {
        return service.getByToken(token).getQrCode();
    }

    @GetMapping("/api/getStatusToken")
    public Boolean getStatusToken(@RequestParam String token) {
        var account = service.getByToken(token);
        return !account.getAccountActiveTime().isBefore(LocalDate.now());
    }
}
