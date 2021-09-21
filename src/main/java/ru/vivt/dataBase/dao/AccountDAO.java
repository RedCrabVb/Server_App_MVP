package ru.vivt.dataBase.dao;

import ru.vivt.dataBase.entity.AccountsEntity;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountDAO {
    void addAccounts(AccountsEntity customer) throws SQLException;
    void updateAccounts(AccountsEntity customer) throws SQLException;
    AccountsEntity getCustomerByID(int customer_id) throws SQLException;
    Collection getAllAccounts() throws SQLException;
    void deleteAccounts(AccountsEntity customer) throws SQLException;
}
