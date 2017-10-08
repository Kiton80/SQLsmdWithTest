package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill on 07.10.2017.
 */
public class AvailableTables implements Command {
    private static String COMMAND_SAMPLE = "AvailableTables";

    private DatabaseManager manager;
    private View view;

    public AvailableTables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return  command.equals(COMMAND_SAMPLE);
    }

    @Override
    public void execute(String str) {

        List<String> result= Arrays.asList( manager.getTableNames());
        for (String r:result) {
            view.write(r);
        }


    }

    @Override
    public int count() {
        return 0;
    }
}
