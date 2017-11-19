package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

/**
 * Created by Kirill on 16.07.2017.
 * Insert|users|username|value1|user_password|value2
 */
//todo delete
public class InsertRow implements Command {
    private static String COMMAND_SAMPLE = "Insert|tableName|column1|value1|column2|value2|...|columnN|valueN";
    private static String HELP_Sample = "InsertRow(deprecated)    формат команды:  " + COMMAND_SAMPLE;

    private final DatabaseManager manager;
    private final View view;

    public InsertRow(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("Insert");
    }

    @Override
    public void execute(String command) throws Exception {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное " +
                    "количество параметров в формате '%s', " +
                    "а ты прислал: '%s'", COMMAND_SAMPLE, command));
        }
        String tableName = data[1];
        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String value = data[index*2 + 1];
            dataSet.put(columnName, value);
        }
        manager.insertRow(tableName, dataSet);
        view.write(String.format("Запись %s была успешно создана в таблице '%s'.", dataSet, tableName));
    }

    @Override
    public int count() {
        return 0 ;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }
}
