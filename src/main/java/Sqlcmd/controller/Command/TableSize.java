package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.util.Arrays;

/**
 * Created by Kirill on 09.11.2017.
 */
public class TableSize implements Command {
    private static String COMMAND_SAMPLE = "tablesaze|TableName";
    private static String COMMAND_DESCRIPTION_SAMPLE = "выводит размер таблицы";
    private static String HELP_Sample = "TableSize    формат команды:  " + COMMAND_SAMPLE + "\t" + COMMAND_DESCRIPTION_SAMPLE;
    private View view;
    private DatabaseManager manager;

    public TableSize(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("tablesize");
    }

    @Override
    public void execute(String command) {
        String[] splitedCommand = command.split("\\|");
        if (splitedCommand.length == 2) {
            String tableName = splitedCommand[1];

            try {
                if (Arrays.asList(manager.getTableNames()).contains(tableName)) {
                    int size = manager.getTableSize(tableName);
                    view.write("Таблица " + tableName + " содержит " + size + " записей.");
                } else {
                    view.write(String.format("Ошибка : Таблица с именем %s не найдена", tableName));
                }

            } catch (Exception e) {
                view.write("Невышло! по причине :" + e.getMessage());
            }

        }

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
