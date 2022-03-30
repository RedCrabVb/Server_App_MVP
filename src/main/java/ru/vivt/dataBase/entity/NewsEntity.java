package ru.vivt.dataBase.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "News")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNews;
    private String title;
    private String body;
    private String imgPath;

    public NewsEntity() {

    }
}
