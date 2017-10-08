package main.java.Sqlcmd.model;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by Kirill on 09.07.2017.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    public Connection connection;

    @Override
    public DataSet[] getTableData(String tableName) {
        String sql ="SELECT * FROM "+tableName;// +"WHERE id_user >2";

        ResultSet rs =

        return new DataSet[0];
    }

    @Override
    public String[] getTableNames() {
        DatabaseMetaData metaData = null;
        String[] result = new String[1000];
        int index = 0;

        try {
            metaData = connection.getMetaData();

            ResultSet resultSet = metaData.getTables(null, "public", "%", new String[]{"TABLE"});

            for (; index < result.length; index++) {
                if (!resultSet.next()) {
                    break;
                }
                result[index] = resultSet.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result = Arrays.copyOf(result, index);
        return result;
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
                            database, userName, password),
                    e);
        }

    }

    @Override
    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            // String[] tableNames=
            stmt.execute("DROP TABLE public. " + "(" + tableName + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            // String[] tableNames=
            stmt.execute("DROP TABLE IF EXISTS public. " + "(" + tableName + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void create(String tableName, DataSet input) {
        try {
            Statement stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    @Override
    public String[] getTableColumns(String tableName) { //toDo

        return new String[0];
    }

    @Override
    public String[] getTabls() {
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
