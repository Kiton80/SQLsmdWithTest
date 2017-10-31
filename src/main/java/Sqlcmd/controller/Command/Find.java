package main.java.Sqlcmd.controller.Command;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.view.View;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kirill on 08.10.2017.
 */
public class Find implements Command {
    private static String COMMAND_SAMPLE = "find|TableName";
    private static int OFFSET = 15;

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isThisCommand(String command) {
        return command.toLowerCase().startsWith("find|");
    }

    @Override
    public void execute(String command) throws SQLException {
        String[] data = command.split("\\|");
        if (command.split("\\|").length!=2){
            view.write(String.format("неправельный формат команды '%s', А Должно быть '%s '." , command,COMMAND_SAMPLE) );
        }
        if (data.length==2){

        String tableName= data[1];
        printTitle(tableName,OFFSET);

        ArrayList<DataSet> tableData= manager.getTableData(tableName);
        printTableData(tableData);

        }
    }


    private void printTableData(ArrayList<DataSet> tableDatas) {
        int ofsetLength=(OFFSET+1)*(tableDatas.get(1).getValues().length);
        String repeated = new String(new char[ofsetLength]).replace("\0", "_");

        for (DataSet tableData: tableDatas) {
            view.write(repeated);
            printRow(tableData,OFFSET-1);
        }
    }

    private void printRow(DataSet tableDatum,int offset) {
        StringBuilder rowSB=new StringBuilder();
        rowSB.append("|");
        Object[] row = tableDatum.getValues();
        for (int j = 0; j <row.length ; j++) {
            rowSB.append(" "+row[j]);
            for (int k = row[j].toString().length(); k <offset ; k++) {
                rowSB.append(" ");
            }
            rowSB.append("|");
        }
        view.write(rowSB.toString());
    }

    private void printTitle(String tableName,int offset) throws SQLException {
        String[] columnNames = manager.getTableColumnsName(tableName);
        StringBuilder rezSB=new StringBuilder();
        rezSB.append("|");
        for (int i = 0; i <columnNames.length; i++) {
            rezSB.append(columnNames[i]);
            for (int j =columnNames[i].length() ; j <offset ; j++) {
                rezSB.append(" ");
            }
            rezSB.append("|");
        }
        String result=rezSB.toString();
        view.write(result);
    }


    @Override
    public int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}
