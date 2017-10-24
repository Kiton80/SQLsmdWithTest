package main.java.Sqlcmd.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill on 09.07.2017.
 */
public interface DatabaseManager {

    //DataSet[] getTableData(String tableName);
    public ArrayList<DataSet> getTableData(String tableName);



    void connect(String database, String userName, String password);

    boolean insert(String tableName, DataSet input) throws Exception;

    void clear(String tableName);

    void deleteTable(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    // String[] getTabls();
   String[] getTableNames();

    boolean isConnected();

    void exit() throws SQLException;

    String[] getTableColumnsName(String tableName);

    List<String> ColumnNamesWithoutAvtoincrement(String tableName);

   // int insert (String tableName,DataSet input);
}
