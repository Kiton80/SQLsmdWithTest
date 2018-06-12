package ua.nikitchenko.Sqlcmd.service;



import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.model.JDBCDatabaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill on 10.02.2018.
 */
public class ServiceImpl implements Service {
    private DatabaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDatabaseManager();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect","list");
    }

    @Override
    public DatabaseManager connect(String databaseName, String userName, String password) throws Exception {
       DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(databaseName, userName, password);
        return manager;
    }
    @Override
    public List<String> list(DatabaseManager manager){
        List<String> result =new ArrayList<>();
        try {
            result=manager.getTableNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
