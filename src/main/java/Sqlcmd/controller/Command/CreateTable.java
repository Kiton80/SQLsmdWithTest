package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill on 29.10.2017.
 * create
 * Команда создает новую таблицу с заданными полями
 * Формат: create|tableName|column1|column2| ... |columnN
 * где: tableName - имя таблицы
 * column1 - имя первого столбца записи
 * column2 - имя второго столбца записи
 * columnN - имя n-го столбца записи
 */
public class CreateTable implements Command {
    private static int offset = 15;
    private static String COMMAND_SAMPLE = "createTable|tableName|column1|column2| ... |columnN";
    private static String COMMAND_SAMPLE_I = "createtable";

    DatabaseManager manager;
    View view;

    public CreateTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        if (command.toLowerCase().startsWith(COMMAND_SAMPLE_I)) {
            return true;
        } else return false;
    }

    @Override
    public void execute(String command) throws Exception {

        String[] splitedCommand = (command.split("\\|"));
        String tableName = splitedCommand[1];
        List<String> columnName = new ArrayList<>();
        for (int i = 2; i < splitedCommand.length; i++) {
            columnName.add(splitedCommand[i]);
        }
        boolean saccses = manager.createTable(tableName, columnName, offset);
        if (saccses) {
            view.write("Успех! Таблица " + tableName + "успешно добавленна");
            printTitle(tableName, offset);

        } else {
            view.write("что-то пошло не так! Таблица " + tableName + " не добавленна");
        }


    }

    @Override
    public int count() {
        return 0;
    }

    private void printTitle(String tableName, int offset) throws SQLException {

        String[] columnNames = new String[0];
        try {
            columnNames = manager.ColumnNamesWithoutAvtoincrement(tableName).toArray(new String[0]);
        } catch (Exception e) {
            view.write("что-то пошло не так" + e.getMessage());
        }
        StringBuilder rezSB = new StringBuilder();
        rezSB.append("|");
        for (int i = 0; i < columnNames.length; i++) {
            rezSB.append(columnNames[i]);
            for (int j = columnNames[i].length(); j < offset; j++) {
                rezSB.append(" ");
            }
            rezSB.append("|");
        }
        String result = rezSB.toString();
        view.write(result);
    }
}
