package ua.nikitchenko.Sqlcmd.controller;


import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.model.JDBCDatabaseManager;
import ua.nikitchenko.Sqlcmd.view.Console;
import ua.nikitchenko.Sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

       MainController controller = new MainController(view, manager);
        controller.run();
    }



}
