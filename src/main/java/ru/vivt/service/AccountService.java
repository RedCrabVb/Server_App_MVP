package ru.vivt.service;

import org.springframework.stereotype.Service;
import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public AccountsEntity getRepositoryById(long id) {
        return repository.findById(id).get();
    }
}
