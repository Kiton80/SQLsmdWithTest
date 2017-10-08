package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Kirill on 08.10.2017.
 */
public class Find implements Command {
    private static String COMMAND_SAMPLE = "find|TableName";

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        if (command.startsWith("find|")&(command.split("\\|").length==2)){
            return true;
        }

        return false;
    }

    @Override
    public void execute(String str) throws SQLException {
       // sql = "SELECT * FROM users WHERE id_user >2";



    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}
