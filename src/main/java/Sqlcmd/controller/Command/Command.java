package main.java.Sqlcmd.controller.Command;



public interface Command {
    String HELP_Sample = "SamText";

    boolean isThisCommand(String command);
    void  execute(String str) throws Exception;
    int count();

    String help();

}
