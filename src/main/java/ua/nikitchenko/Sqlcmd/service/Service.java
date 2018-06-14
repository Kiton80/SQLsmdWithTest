package ua.nikitchenko.Sqlcmd.service;

import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import java.util.List;

public interface Service {
    List<String> commandsList();

    DatabaseManager connect(String databaseName, String userName, String password) throws Exception;

    List<String> list(DatabaseManager manager);
    List<List<String>> find(DatabaseManager manager,String tableName) throws Exception;
}
