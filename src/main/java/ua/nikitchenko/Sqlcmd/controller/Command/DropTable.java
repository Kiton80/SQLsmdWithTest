package ua.nikitchenko.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;


public class DropTable implements Command {
    private static String COMMAND_SAMPLE = "dropTable|TableName";
    private static String HELP_Sample = "DropTable    формат команды:  " + COMMAND_SAMPLE;



    private final DatabaseManager manager;
    private final View view;

    public DropTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {

        return command.toLowerCase().startsWith("droptable");
    }

    @Override
    public void execute(String str) {
        String[] splitedCommand =str.split("\\|");
        try {
            manager.dropTable(splitedCommand[1]);
            view.write(String.format("Таблица %s  была успешно удалена. ", splitedCommand[1]));
        } catch (Exception e) {
            view.write(String.format("Таблица %s не была удалена. Причина : ", splitedCommand[1]) + e.getMessage());
        }
    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("\\|").length ;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }
}
