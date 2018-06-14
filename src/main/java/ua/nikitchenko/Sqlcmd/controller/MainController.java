package ua.nikitchenko.Sqlcmd.controller;


import ua.nikitchenko.Sqlcmd.controller.Command.*;
import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.view.View;

class MainController {

    private Command[] commands;
    private View view;

    MainController(View view, DatabaseManager manager) {
        this.view = view;
        Help help = new Help(view);
        this.commands = new Command[] {
                new Connect(manager, view),
                new Exit(view,manager),
                new DropTable(manager, view),
                new List(manager, view),
//                new Find( manager,view),
                new InsertRow(manager, view),
                new CreateTable(manager, view),
                new TableSize(manager, view),
//                new IsConnected(manager, view),
//                new Clear(manager, view),
                new Update(manager, view),
                new InsertRow(manager, view),
//                new Find(manager, view),
                help,
                new Unsupported(view),
        };
        help.setCommands(this.commands);
    }

    void run() {
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
