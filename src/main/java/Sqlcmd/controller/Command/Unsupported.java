package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 07.10.2017.
 */
public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {

        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return true;
    }

    @Override
    public void execute(String str) throws SQLException {
        view.write(String.format("\tКоманды '%s' не существует", str));

    }

    @Override
    public int count() {
        return 0;
    }
}
