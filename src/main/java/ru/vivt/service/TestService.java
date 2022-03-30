package ru.vivt.service;

import org.springframework.stereotype.Service;
import ru.vivt.dataBase.entity.TestEntity;
import ru.vivt.repository.TestRepository;

import java.util.List;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

}
