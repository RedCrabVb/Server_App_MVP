package ru.vivt.dataBase;

import com.google.gson.JsonObject;

import java.sql.SQLException;

public interface DataBase {
    JsonObject lastNews(int number);
    JsonObject generateAccount();
    JsonObject setPersonData(JsonObject jsonPersonData);
    JsonObject getQrCode(int id);
/*    String authorization(String login, String password) throws Exception;
    void registration(String name, String surname, String patronymic, String groups, String mail, String password);

    int getIDForToken(String token);

    JsonObject personData(Student student) throws Exception;
    JsonObject schedule(Student student) throws SQLException;
    JsonObject news() throws SQLException;
    JsonObject importantDates() throws SQLException;
    JsonObject message(Student student) throws SQLException;
    JsonObject academicPerformance(int ID) throws SQLException;

    boolean sendMessage(int sender, int recipient, String header, String text);
    int getIDbyMail(String mail);

    Student getStudentForToken(String token);*/
}