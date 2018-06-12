package ua.nikitchenko.Sqlcmd.controller.Command;



import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;


public class CreateTable implements Command {
    private static int OFFSET = 15;
    private static String COMMAND_SAMPLE = "createTable|tableName|column1|column2| ... |columnN";
    private static String COMMAND_SAMPLE_I = "createtable";
    private static String HELP_Sample = "CreateTable    формат команды:  " + COMMAND_SAMPLE;

    private DatabaseManager manager;
    private View view;

    public CreateTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith(COMMAND_SAMPLE_I);
    }

    public void execute(String command) {
        String[] splitedCommand = command.split("\\|");
        String tableName = splitedCommand[1];
        ArrayList columnName = new ArrayList();
        columnName.addAll(Arrays.asList(splitedCommand).subList(2, splitedCommand.length));
        try {
            if (!manager.getTableNames().contains(tableName)) {
                manager.createTable(tableName, columnName, OFFSET);
                view.write("Успех! Таблица " + tableName + " успешно добавленна. вот ее поля ");
                printTitle(tableName, OFFSET);
            } else {
                view.write("таблица с именем " + tableName + " уже существует.");
            }
        } catch (Exception e) {
            view.write("что-то пошло не так! Таблица " + tableName + " не добавленна " + e.getMessage());
        }

    }

    public int count() {
        return 0;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }

    private void printTitle(String tableName, int offset) {
        String[] columnNames = new String[0];
        try {
            columnNames = manager.ColumnNamesWithoutAvtoincrement(tableName).toArray(new String[0]);
        } catch (Exception e) {
            view.write("что-то пошло не так!" + e.getMessage());
        }

        StringBuilder rezSB = new StringBuilder();
        rezSB.append("|");
        for (String columnName : columnNames) {
            rezSB.append(columnName);
            for (int j = columnName.length(); j < offset; ++j) {
                rezSB.append(" ");
            }
            rezSB.append("|");
        }
        String result = rezSB.toString();
        this.view.write(result);
    }

}
