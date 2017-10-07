package main.java.Sqlcmd.controller;

import main.java.Sqlcmd.controller.Command.*;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

/**
 * Created by Kirill on 15.07.2017.
 */
public class MainController {

    private Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[] {
                new Connect(manager, view),
                new Exit(view,manager),
                new Help(view),
                new Delete(manager,view),
//                new IsConnected(manager, view),
               new AvailableTables(manager, view),
//                new Clear(manager, view),
                new Create(manager, view)
//                new Find(manager, view),
//                new Unsupported(view)
        };
    }

    public void run() {
        try {
            doWork();
        }
        catch (ExitException e) {
            // do nothing
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWork() throws Exception {
        view.write("Привет юзер!");
        view.write("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password");

        while (true) {
            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.isThisCommand(input)) {
                        command.execute(input);
                        break;
                    }
                }
                catch (Exception e) {
                    if (e instanceof ExitException) {

                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Введи команду (или help для помощи):");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Неудача! по причине: " + message);
        view.write("Повтори попытку.");
    }

}
