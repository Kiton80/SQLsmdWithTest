package ua.nikitchenko.Sqlcmd.controller;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.model.JDBCDatabaseManager;
import main.java.Sqlcmd.view.Console;
import main.java.Sqlcmd.view.View;

/**
 * Created by Kirill on 15.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        main.java.Sqlcmd.controller.MainController controller = new main.java.Sqlcmd.controller.MainController(view, manager);
        controller.run();
    }



}
