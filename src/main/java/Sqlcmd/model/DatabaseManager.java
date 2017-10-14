package main.java.Sqlcmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Kirill on 09.07.2017.
 */
public interface DatabaseManager {

    //DataSet[] getTableData(String tableName);
    public List<Map<String, Object>> getTableData(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void delete(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);
    String[] getTabls();

    boolean isConnected();

    void exit() throws SQLException;
}
