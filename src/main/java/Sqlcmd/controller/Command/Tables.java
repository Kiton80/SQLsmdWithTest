package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kirill on 13.10.2017.
 */
public class Tables implements Command {
    private static String COMMAND_SAMPLE ="tables|";
    private View view;
    private DatabaseManager manager;

    public Tables( DatabaseManager manager,View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().equals(COMMAND_SAMPLE);
    }

    @Override
    public void execute(String str) throws SQLException {
        String[] tabelNams= manager.getTableNames();
        for (int i = 0; i <tabelNams.length ; i++) {
            view.write(tabelNams[i]);

        }

    }

    @Override
    public int count() {
        return 0;
    }
}
