package ru.vivt.dataBase;

import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.AccountDAOImp;
import ru.vivt.dataBase.dao.ResetPasswordDAOImp;

public class Factory {
    private static AccountDAO accountDAO;
    private static ResetPasswordDAOImp resetPasswordDAO;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public AccountDAO getAccountDAO(){
        if (accountDAO == null){
            accountDAO = new AccountDAOImp();
        }
        return accountDAO;
    }

    public ResetPasswordDAOImp getResetPasswordDAO() {
        if (resetPasswordDAO == null){
            resetPasswordDAO = new ResetPasswordDAOImp();
        }
        return resetPasswordDAO;
    }
}
