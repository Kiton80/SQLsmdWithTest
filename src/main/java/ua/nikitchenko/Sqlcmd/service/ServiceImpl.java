package ua.nikitchenko.Sqlcmd.service;

import ua.nikitchenko.Sqlcmd.model.DataSet;
import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.model.JDBCDatabaseManager;

import java.util.*;


public class ServiceImpl implements Service {


    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect","list","find");
    }

    @Override
    public DatabaseManager connect(String databaseName, String userName, String password) throws Exception {
       DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(databaseName, userName, password);
        return manager;
    }
    @Override
    public List<String> list(DatabaseManager manager){
        List<String> result =new ArrayList<>();
        try {
            result=manager.getTableNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager,String tableName) throws Exception {
        List<List<String>> result= new LinkedList<>();
        //manager.getTableColumns(tableName);
        List<String> columns= new LinkedList<>(manager.getTableColumns(tableName));

        List<DataSet> tableData= manager.getTableData(tableName);
        result.add(columns);
        for (DataSet dataSet: tableData) {
            List<String> row = new ArrayList<>(columns.size());
            result.add(row);
            for (String column: columns) {
                row.add(dataSet.get(column).toString());
            }
        }
        return result;
    }

}
