package ua.nikitchenko.Sqlcmd.service;



import main.java.Sqlcmd.model.DatabaseManager;

import java.util.List;

/**
 * Created by Kirill on 10.02.2018.
 */
public interface Service {
    List<String> commandsList();

    DatabaseManager connect(String databaseName, String userName, String password) throws Exception;

    List<String> list(DatabaseManager manager);
}
