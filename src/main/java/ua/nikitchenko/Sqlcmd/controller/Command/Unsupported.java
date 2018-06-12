package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

public class Unsupported implements Command {
    private static final String HELP_Sample = "Unsupported    формат команды:  команда генерируется самостоятельно";
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

    @Override
    public String help() {
        return HELP_Sample;
    }
}
