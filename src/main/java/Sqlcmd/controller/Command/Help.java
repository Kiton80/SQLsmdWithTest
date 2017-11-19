package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.view.View;


public class Help implements Command {
    private static String COMMAND_SAMPLE ="help";
    private static String HELP_Sample = "help    формат команды:  " + COMMAND_SAMPLE;

    private Command[] commands;
    private View view;

    public Help(View view) {
        this.view = view;
    }

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("help");
    }

    @Override
    public void execute(String str) throws Exception {
        for (Command command : commands) {
            // view.write(command.getClass().getName());
            view.write(command.help());
        }
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }
}
