package main.java.Sqlcmd.model;

import java.sql.SQLException;

/**
 * Created by Kirill on 09.07.2017.
 */
public interface DatabaseManager {

    //DataSet[] getTableData(String tableName);
    public DataSet[] getTableData(String tableName);



    void connect(String database, String userName, String password);

    void clear(String tableName);

    void delete(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    // String[] getTabls();
   String[] getTableNames();

    boolean isConnected();

    void exit() throws SQLException;
}
