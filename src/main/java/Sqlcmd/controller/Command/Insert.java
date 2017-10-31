package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;


//user_name|kir|user_password|ddddd
//   connect|testSQLcmd|postgres|123456
public class Insert implements Command {
    private static String COMMAND_SAMPLE = "insert|tn";
    private static String COMMAND_SAMPLE_II = "column1|value1|column2|value2| ... | columnN | valueN";
    private static int OFFSET = 15;

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return (command.toLowerCase().startsWith("insert"));
    }

    @Override
    public void execute(String command) {
        String[] splitedCommand = command.split("\\|");
        String tableName = new String(splitedCommand[1]);
        if (validateFerstInput(tableName)) {
            view.write("Введите команду exit для выхода из команды," +
                    " или введите параметры вставляемой записи(количество полей должно быть четное).\n" +
                    "формат: " + COMMAND_SAMPLE_II + "\n" +
                    "вот поля для заполнения в таблице которую вы выбрали" +
                    "(автоинкрементируемые поля будут добавленны автоматически).\n");
            printTitle(tableName);
            String secondInput = view.read();
            if (!secondInput.toLowerCase().startsWith("exit")) {
                Insert(tableName, secondInput);
            } else {
                view.write("вы вышли из команды insert");
            }
        }
    }

    private boolean validateFerstInput(String tableName) {
        boolean result = false;
        try {
            String[] tableNames = manager.getTableNames();
            for (int i = 0; i < tableNames.length; i++) {
                if (tableNames[i].equals(tableName)) {
                    view.write(String.format("Таблица %s найдена в базе:", tableName));
                    result = true;
                }
            }
        } catch (Exception e) {
            view.write(String.format("Таблица %s НЕнайдена в базе:", tableName));
            view.write(e.getMessage());
        }
        return result;
    }

    private void Insert(String tableName, String secondInput) {
        String[] splitedInput = secondInput.split("\\|");
        if (splitedInput.length % 2 == 0) {
            DataSet inputedDataSet = new DataSet();
            for (int i = 0; i < splitedInput.length / 2; i++) {
                inputedDataSet.put(splitedInput[i * 2], splitedInput[i * 2 + 1]);
            }
            try {
                manager.create(tableName, inputedDataSet);
                view.write("В таблицу " + tableName + " успешно вставленны данные.");
            } catch (Exception e) {
                view.write("что-то пошло не так" + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(String.format(
                    "неверное количество полей разделенное '|'," +
                            " должно быть четное а вы ввели %s", splitedInput.length));
        }

    }

    private void printTitle(String tableName) {
        String[] columnNames = new String[0];
        try {
            columnNames = manager.ColumnNamesWithoutAvtoincrement(tableName).toArray(new String[0]);
        } catch (Exception e) {
            view.write("что-то пошло не так");
            view.write(e.getMessage());
        }
        String title = levelingTable(columnNames);
        view.write(title);
    }

    private String levelingTable(String[] columnNames) {
        StringBuilder rezSB = new StringBuilder();
        rezSB.append("|");
        for (int i = 0; i < columnNames.length; i++) {
            rezSB.append(columnNames[i]);
            for (int j = columnNames[i].length(); j < OFFSET; j++) {
                rezSB.append(" ");
            }
            rezSB.append("|");
        }
        return rezSB.toString();
    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("|").length;
    }
}
