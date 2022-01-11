package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface AccountDAO<T extends Collection> {
    void addAccounts(AccountsEntity customer);
    void updateAccounts(AccountsEntity customer) throws SQLException;
    AccountsEntity getAccountByID(int customer_id) throws SQLException;
    AccountsEntity getAccountByToken(String token);
    AccountsEntity getAccountByEmailAndPassword(String email, String password);
    List<AccountsEntity> getAccountByEmail(String email);
    T getAllAccounts() throws SQLException;
    void deleteAccounts(AccountsEntity customer) throws SQLException;
}
