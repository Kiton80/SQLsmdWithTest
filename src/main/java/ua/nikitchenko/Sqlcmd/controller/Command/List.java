package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.util.ArrayList;

public class List implements Command {
    private static String COMMAND_SAMPLE = "list";
    private static String HELP_Sample = "Update    формат команды:   list";
    private View view;
    private DatabaseManager manager;

    public List(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().equals(COMMAND_SAMPLE);
    }

    @Override
    public void execute(String str) {
        ArrayList<String> tabelNams;
        try {
            tabelNams = manager.getTableNames();
            StringBuilder sb = new StringBuilder();
            view.write("найденно "+tabelNams.size()+" таблиц");
            for (int i = 0; i < tabelNams.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                    sb.append(tabelNams.get(i));
                } else {
                    sb.append(tabelNams.get(i));
                }
            }
            view.write(sb.toString());
        } catch (Exception e) {
            view.write("что-то пошло не так" + e.getMessage());
        }

    }

    @Override
    public int count() {
        return 1;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }
}
