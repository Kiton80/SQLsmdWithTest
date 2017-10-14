package main.java.Sqlcmd.model;

import main.java.Sqlcmd.model.DataSet;
import main.java.Sqlcmd.model.DatabaseManager;

import java.sql.*;
import java.util.*;

/**
 * Created by Kirill on 09.07.2017.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    public Connection connection;

//    @Override
//    public DataSet[] getTableData(String tableName) {
//        String sql ="SELECT * FROM "+tableName;// +"WHERE id_user >2";
//
//       // ResultSet rs =
//
//        return new DataSet[0];
//    }
    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        List<Map<String, Object>> result = new LinkedList<>();
        // try-with-resources statement ensures that each resource is closed at the end of the statement
        try (Statement stmt = connection.createStatement();
             ResultSet tableData = stmt.executeQuery("SELECT * FROM public." + tableName)) {
            ResultSetMetaData metaData = tableData.getMetaData();

            while (tableData.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    data.put(metaData.getColumnName(i), tableData.getObject(i));
                }
                result.add(data);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public String[] getTableNames() {

        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet tableNames = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            while (tableNames.next()) {
                tables.add(tableNames.getString("table_name"));
            }
            return tables.toArray(new String[tables.size()]);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
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
