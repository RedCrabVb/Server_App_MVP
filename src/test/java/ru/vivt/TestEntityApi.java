package ru.vivt;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestEntityApi {

    @Test
    public void test() {
        var list = List.of(5, 5, 7, 1, 3, 2, 0);
        var listPart = ListUtils.partition(list, 2);
        listPart.forEach(x -> {
            x.forEach(System.out::println);
            System.out.println("===");
        });
    }

}