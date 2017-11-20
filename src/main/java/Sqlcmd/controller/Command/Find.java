package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import java.util.ArrayList;


public class Find implements Command {
    private static String COMMAND_SAMPLE = "find|tableName";
    private static String HELP_Sample = "Find    формат команды:  " + COMMAND_SAMPLE;
    private static int OFFSET = 15;

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("find");
    }

    @Override
    public void execute(String command) {
        String[] data = command.split("\\|");
        if (command.split("\\|").length != 2) {
            view.write(String.format("неправельный формат команды '%s', А Должно быть '%s '.", command, COMMAND_SAMPLE));
        }
        if (data.length == 2) {
            String tableName = data[1];
            ArrayList<DataSet> tableData;
            try {
                if (tableNameChecking(tableName)) {
                    printTitle(tableName, OFFSET);
                    tableData = manager.getTableData(tableName);
                    printTableData(tableData);
                } else {
                    view.write("Таблица с именем '" + tableName + "' не найдена в базе.");
                }
            } catch (Exception e) {
                view.write("Что-то пошло не так. По причине:  " + e.getMessage());
            }
        }
    }


    @Override
    public int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }

    @Override
    public String help() {
        return HELP_Sample;
    }

    private boolean tableNameChecking(String tableName) throws Exception {
        boolean flag = false;
        ArrayList<String> tableNames = manager.getTableNames();
        for (String tableName1 : tableNames) {
            if (tableName.equals(tableName1)) {
                flag = true;
            }
        }
        return flag;
    }

    private void printTableData(ArrayList<DataSet> tableDatas) {
        int ofsetLength = (OFFSET + 1) * (tableDatas.get(1).getValues().length);
        String repeated = new String(new char[ofsetLength]).replace("\0", "_");
        for (DataSet tableData : tableDatas) {
            view.write(repeated);
            printRow(tableData, OFFSET - 1);
        }
    }

    private void printRow(DataSet tableDatum, int offset) {
        StringBuilder rowSB = new StringBuilder();
        int lenght;
        rowSB.append("|");
        Object[] row = tableDatum.getValues();
        for (Object aRow : row) {
            if (aRow != null) {
                rowSB.append(" " + aRow);
                lenght = aRow.toString().length();
            } else {
                rowSB.append(" ");
                lenght = 1;
            }
            for (int k = lenght; k < offset; k++) {
                rowSB.append(" ");
            }
            rowSB.append("|");
        }
        view.write(rowSB.toString());
    }

    private void printTitle(String tableName, int offset) throws Exception {
        String[] columnNames;
        try {
            columnNames = manager.getTableColumnsName(tableName);
            StringBuilder rezSB = new StringBuilder();
            rezSB.append("|");
            for (int i = 0; i < columnNames.length; i++) {
                rezSB.append(columnNames[i]);
                for (int j = columnNames[i].length(); j < offset; j++) {
                    rezSB.append(" ");
                }
                rezSB.append("|");
            }
            String result = rezSB.toString();
            view.write(result);

        } catch (Exception e) {
//            view.write("что-то случилось в методе принт тайтл причина: "+e.getMessage());
            throw new Exception("что-то случилось в методе Принт Тайтл причина: " + e.getMessage());
        }
    }

}