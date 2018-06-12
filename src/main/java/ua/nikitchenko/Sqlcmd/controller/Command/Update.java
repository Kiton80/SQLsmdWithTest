package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.util.ArrayList;

//update|users|user_password|sdfsdfsadf|pass
public class Update implements Command {
    // private static final String COMMAND_SAMPLE = "Update|tableName|columnForeSelect|ValueForSelect|nawValue1";
    private static final String COMMAND_SAMPLE_I = "update";
    private static final String HELP_Sample = "Update    формат команды:  Update|tableName|targetColumn|exchengingValue|nawValue1";


    private final DatabaseManager manager;
    private final View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith(COMMAND_SAMPLE_I);
    }

    @Override
    public void execute(String command) {
        String[] splitedCommand = command.split("\\|");
        String tableName = splitedCommand[1];
        String columnForChenging = splitedCommand[2];
        String oldVelue = splitedCommand[3];
        String newValue = splitedCommand[4];
        if (checkinTableName(tableName)) {
            if (checkinColumnForSelect(tableName, columnForChenging)) {
                try {
                    int countResult = manager.updateTable(tableName, columnForChenging, oldVelue, newValue);
                    view.write(String.format("Данные в количестве %s записи(ей) добавленны в таблицу %s", countResult, tableName));
                } catch (Exception e) {
                    view.write("Невышло! по причине :" + e.getMessage());
                }
            }
        }
    }

    private boolean checkinColumnForSelect(String tableName, String columnForeSelect) {

        try {
            String[] columnNames = manager.getTableColumnsName(tableName);
            for (String columnName : columnNames) {
                if (columnForeSelect.equals(columnName)) {
                    return true;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            view.write(String.format("Ошибка : В таблице с именем %s не найдена колонка с именем %s", tableName, columnForeSelect));
        }
        return false;
    }

    private boolean checkinTableName(String tableName) {
        try {
            ArrayList<String> tableNames = manager.getTableNames();
            for (String tableName1 : tableNames) {
                if (tableName.equals(tableName1)) {
                    return true;
                } else {
                    throw new Exception("не найденна таблица");
                }
            }
        } catch (Exception e) {
            view.write(String.format("Ошибка : Таблица с именем %s не найдена", tableName));
        }
        return false;
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
