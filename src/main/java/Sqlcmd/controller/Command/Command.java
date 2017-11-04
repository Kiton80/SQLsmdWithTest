package main.java.Sqlcmd.controller.Command;


import java.sql.SQLException;

/**
 * Created by Kirill on 15.07.2017.
 */
public interface Command {

    boolean isThisCommand(String command);
    void  execute(String str) throws Exception;
    int count();



}
