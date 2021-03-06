package ua.nikitchenko.Sqlcmd.controller.Command;


import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.view.View;

public class Connect  implements Command {
    private static String HELP_Sample = "Connect    формат команды:  connect|testSQLcmd|postgres|123456";
    private static String COMMAND_SAMPLE = "connect|testSQLcmd|postgres|123456";


    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }


    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("connect");
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
        try {
            manager.connect(databaseName, userName, password);
            view.write("Успех!");
        } catch (Exception e) {
            view.write("Подключение не состоялось! Проверте правельность указания параметров.");
        }
    }

    @Override
     public int count() {
    return COMMAND_SAMPLE.split("\\|").length;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }

}
