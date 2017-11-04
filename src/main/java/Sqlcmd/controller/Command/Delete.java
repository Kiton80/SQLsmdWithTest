package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 07.10.2017.
 */
public class Delete implements Command {

    private static String COMMAND_SAMPLE = "deleteTable|TableName";


    private final DatabaseManager manager;
    private final View view;

    public Delete(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {

        return command.toLowerCase().startsWith("deleteTable|");
    }

    @Override
    public void execute(String str) throws SQLException {
        String[] splitedCommand =str.split("\\|");
        manager.deleteTable(splitedCommand[1]);


    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("\\|").length ;
    }
}
