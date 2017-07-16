package main.java.Sqlcmd.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Kirill on 09.07.2017.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    public Connection connection;
    @Override
    public DataSet[] getTableData(String tableName) {
        return new DataSet[0];
    }

    @Override
    public String[] getTableNames() {
        return new String[0];
    }

    @Override
    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName,
                    password);
           // System.out.println(" Access connection  - ok.");
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s password: %s",
                            database, userName,password),
                    e);
        }

    }

    @Override
    public void clear(String tableName) {

    }

    @Override
    public void create(String tableName, DataSet input) {

    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {

    }

    @Override
    public String[] getTableColumns(String tableName) {
        return new String[0];
    }

    @Override
    public boolean isConnected() {
        return false;
    } //TODO

    @Override
    public void exit() throws SQLException {

            connection.close();

    }
}
