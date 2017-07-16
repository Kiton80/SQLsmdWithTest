package main.java.Sqlcmd.Command;

import main.java.Sqlcmd.controller.DatabaseManager;
import main.java.Sqlcmd.view.View;

/**
 * Created by Kirill on 15.07.2017.
 */
public class Connect  implements Command {
    private static String COMMAND_SAMPLE = "connect|testSQLcmd|postgres|123456";

    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }


    @Override
    public boolean isThisCommand(String command) {
    return command.toLowerCase().startsWith("connect|");
    }

    @Override
    public void execute(String command) {

        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(
                    String.format("Неверно количество параметров разделенных " +
                                    "знаком '|', ожидается %s, но есть: %s",
                            count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);

        view.write("Успех!");

    }

    @Override
     public int count() {
    return COMMAND_SAMPLE.split("\\|").length;
    }

}
