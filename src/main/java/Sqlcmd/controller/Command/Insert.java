package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 20.10.2017.
 */
//user_name|kir|user_password|ddddd
//    user_name|git|user_password|hab
public class Insert implements Command {
    private static String COMMAND_SAMPLE = "insert|tableName";
    private static String COMMAND_SAMPLE_II = "column1|value1|column2|value2| ... | columnN | valueN";
    private static String COMMAND_SAMPLE_III = "user_name|kir|user_password|ddddd";
    private static int OFFSET = 15;

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {

        return (command.toLowerCase().startsWith("insert|"));
    }

    @Override
    public void execute(String command) throws Exception {
        String[] splitedCommand = command.split("\\|");
        String tableName = new String(splitedCommand[1]);
        String[] tableNames = manager.getTableNames();
        String input = "";
        boolean flag = false, twoflag = false;
        for (int i = 0; i < tableNames.length; i++) {
            if (tableNames[i].equals(tableName)) {
                flag = true;
            }
        }
        if (flag) {
            view.write(String.format("Таблица %s найдена в базе:", tableName));
            view.write("Введите команду exit для выхода из команды. Или введите параметры вставляемой записи.");
            view.write("формат: " + COMMAND_SAMPLE_II);
        } else {
            view.write(String.format("Таблица %s НЕнайдена в базе:", tableName));
        }

        if (Insert(tableName)) {
            view.write("запись добавленна в таблицу" + tableName);
        }


    }

    private boolean Insert(String tableName) throws Exception {
        String input;
        boolean twoflag;
        view.write("вот поля в таблице которую вы выбрали");
        printTitle(tableName, OFFSET);
        input = view.read();
        if (input.equals("exit")) {
            view.write("вы вышли из команды insert");
            twoflag = true;
        }
        twoflag = insertDataInTable(input, tableName);

        return twoflag;
    }

    @Override
    public int count() {
        return COMMAND_SAMPLE.split("|").length;
    }

    private boolean insertDataInTable(String input, String tableName) throws Exception {
        String[] splitedInput = input.split("\\|");
        DataSet inputedDataSet = new DataSet();
        for (int i = 0; i < splitedInput.length / 2; i++) {
            inputedDataSet.put(splitedInput[i * 2], splitedInput[i * 2 + 1]);
        }
        return manager.create(tableName, inputedDataSet);
    }

    private void printTitle(String tableName, int offset) throws SQLException {

        String[] columnNames = manager.ColumnNamesWithoutAvtoincrement(tableName).toArray(new String[0]);
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

    // todo -refactoring code. maybe something will be simplified.
    private boolean checkingFirstInput(String tableName, String[] tabelNams) throws SQLException {
        Boolean flag = false;
        for (int i = 0; i < tabelNams.length; i++) {
            if (tabelNams[i].equals(tableName)) {
                flag = !flag;
            }
        }
        if (flag) {
            view.write(String.format("Таблица %s найдена в базе. вот ее поля:", tableName));
            printTitle(tableName, OFFSET);
        } else {
            view.write(String.format("В базе данных нет таблицы c указанным именем \" %s \".", tableName));

        }
        return flag;
    }
}
