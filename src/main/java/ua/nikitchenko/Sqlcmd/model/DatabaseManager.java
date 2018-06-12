package ua.nikitchenko.Sqlcmd.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseManager {

    //DataSet[] getTableData(String tableName);
    ArrayList<DataSet> getTableData(String tableName) throws Exception;

    boolean createTable(String tableName, List<String> input, int offset) throws Exception;


    void connect(String database, String userName, String password) throws Exception;

    void dropTable(String tableName) throws Exception;

    void insertRow(String tableName, DataSet input) throws Exception;

    void update(String tableName, int id, DataSet newValue);

    int updateTable(String tableName, String calumnForupdate, String velueForSelect, String newVelue) throws Exception;

    String[] getTableColumns(String tableName);

    int getTableSize(String tableName) throws Exception;

    ArrayList<String> getTableNames() throws Exception;

    boolean isConnected();

    void exit() throws SQLException;

    String[] getTableColumnsName(String tableName);

    List<String> ColumnNamesWithoutAvtoincrement(String tableName) throws Exception;


}
