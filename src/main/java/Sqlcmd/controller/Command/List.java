package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

public class List implements Command {
    private static String COMMAND_SAMPLE = "list";
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
        String[] tabelNams;
        try {
            tabelNams = manager.getTableNames();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tabelNams.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(tabelNams[i]);
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
}
