package main.java.Sqlcmd.controller;

import main.java.Sqlcmd.view.Console;
import main.java.Sqlcmd.view.View;

/**
 * Created by Kirill on 15.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }



}
