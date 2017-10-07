package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 15.07.2017.
 */
public class Exit implements Command{
    private static String COMMAND_SAMPLE = "Exit";
    View view;
    DatabaseManager databaseManager;

    public Exit(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean isThisCommand(String command) {
        return  command.toLowerCase().startsWith(COMMAND_SAMPLE.toLowerCase());

    }

    @Override
    public void execute(String command) throws SQLException {
        System.out.println("Закрываю работу служб : ");

        if (databaseManager.isConnected()) { databaseManager.exit(); }
        System.out.println("_______________________ ok");

        throw new ExitException();
    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("|").length;
    }
}
