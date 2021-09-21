package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.AccountsEntity;
import ru.vivt.dataBase.entity.ResetPasswordEntity;

import java.sql.SQLException;

public interface ResetPasswordDAO {
    void addResetPassword(ResetPasswordEntity customer) throws SQLException;
    ResetPasswordEntity getResetPasswordByToken(String token) throws SQLException;
    void deleteResetPassword(ResetPasswordEntity customer) throws SQLException;
}
