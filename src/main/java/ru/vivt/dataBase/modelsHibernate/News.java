package ru.vivt.dataBase.modelsHibernate;

import javax.persistence.*;

@Entity
@Table(name = "News")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNews;
    private String title;
    private String body;
    private String imgPath;
}
