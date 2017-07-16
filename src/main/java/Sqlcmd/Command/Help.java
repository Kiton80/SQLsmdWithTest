package main.java.Sqlcmd.Command;

import main.java.Sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Kirill on 15.07.2017.
 */
public class Help implements Command {
    private static String COMMAND_SAMPLE ="help";
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().equals("help");
    }

    @Override
    public void execute(String str)  {
        String offset1="\t\t\t\t\t\t";
        String offset2="\t\t\t\t\t\t\t\t\t";
        view.write  ("Существующие команды:"+"\t\t\t\t\t"+"синтаксис");

        view.write("connect:" +"\t\t\t\t\t"+"connect|databaseName|userName|password");
        view.write(offset2+"для подключения к базе данных, с которой будем работать");

        view.write("help:"+offset1+"help");
        view.write(offset2+"для вывода этого списка на экран(можно использовать любой регистр)");

        view.write("exit:"+offset1+"exit");
        view.write(offset2+"для выхода из программы(можно использовать любой регистр)");

        view.write("create"+offset1+"create|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write(offset2+"для создания записи в таблице");


    }

    @Override
    public int count() {
        return 0;
    }
}
