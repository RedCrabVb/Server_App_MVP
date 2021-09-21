package ru.vivt.dataBase;

import ru.vivt.dataBase.dao.AccountDAO;
import ru.vivt.dataBase.dao.AccountDAOImp;

public class Factory {
    private static AccountDAO accountDAO = null;
//    private static ItemDAO ItemDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

/*    public ItemDAO getItemDAO(){
        if (ItemDAO == null){
            ItemDAO = new ItemDAOImpl();
        }
        return ItemDAO;
    }*/

    public AccountDAO getAccountDAO(){
        if (accountDAO == null){
            accountDAO = new AccountDAOImp();
        }
        return accountDAO;
    }
}
