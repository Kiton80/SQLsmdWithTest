package ua.nikitchenko.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;


public class Exit implements Command{
    private static String COMMAND_SAMPLE = "Exit";
    private static String HELP_Sample = "Exit    формат команды:  exit";
    private View view;
    private DatabaseManager manager;

    public Exit(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
    }

    @Override
    public boolean isThisCommand(String command) {
        return  command.toLowerCase().startsWith(COMMAND_SAMPLE.toLowerCase());

    }

    @Override
    public void execute(String command) throws SQLException {
        System.out.println("Закрываю работу служб: ");

        if (manager.isConnected()) {
            manager.exit();
        }
        System.out.println("_______________________ ok");

        throw new ExitException();
    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("|").length;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }
}
