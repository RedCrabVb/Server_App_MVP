package ru.vivt.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vivt.repository.AccountRepository;
import ru.vivt.repository.ResetPasswordRepository;
import ru.vivt.repository.ResultTestRepository;

@Controller
@RequestMapping(path = "app")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Autowired
    private ResultTestRepository resultTestRepository;

    @GetMapping("user_info")
    public String userInfo(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        return "user_info";
    }

    @Transactional
    @GetMapping("deleteUser")
    public String deleteUser(@RequestParam Long idUser) {
        resetPasswordRepository.deletedByIdUser(idUser);
        resultTestRepository.deletedByIdUser(idUser);
        accountRepository.deleteById(idUser);
        return "redirect:user_info";
    }

}
