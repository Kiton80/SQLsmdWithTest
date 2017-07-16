package main.java.Sqlcmd.Command;

import main.java.Sqlcmd.controller.DataSet;
import main.java.Sqlcmd.controller.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 16.07.2017.
 */
public class Create implements Command{


    private final DatabaseManager manager;
    private final View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void execute(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное " +
                    "количество параметров в формате " +
                    "'create|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "а ты прислал: '%s'", command));
        }

        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String value = data[index*2 + 1];

            dataSet.put(columnName, value);
        }
        manager.create(tableName, dataSet);

        view.write(String.format("Запись %s была успешно создана в таблице '%s'.", dataSet, tableName));

    }

    @Override
    public int count() {
        return 1;
    }
}
