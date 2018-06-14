package ua.nikitchenko.Sqlcmd.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCDatabaseManager implements DatabaseManager {
    public Connection connection;

    @Override
    public ArrayList<DataSet> getTableData(String tableName) throws Exception {
        ArrayList<DataSet> resultDataSet;
    try(Statement stmt=connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName)) {
        ResultSetMetaData rsmd = rs.getMetaData();
        resultDataSet = new ArrayList<DataSet>();

        while (rs.next()) {
            DataSet dataSet = new DataSet();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
            }
            resultDataSet.add(dataSet);
        }
    } catch (SQLException e) {
        throw new Exception(e.getMessage());
    }
        return resultDataSet;
    }

    @Override
    public boolean createTable(String tableName, List<String> input, int offset) throws Exception {

        boolean saccses = false;
        StringBuilder sb = new StringBuilder();
        for (String name : input) {
            sb.append("," + name + " VARCHAR (" + offset + ") ");
        }

        try (Statement stm = connection.createStatement()) {
            String sql = String.format("CREATE TABLE public." + tableName + "(id_" + tableName +
                    " SERIAL PRIMARY KEY NOT NULL  %s )", sb.toString());
            if (stm.executeUpdate(sql) > 0) {
                saccses = true;
            }

        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }


        return saccses;
    }

    @Override
    public void connect(String database, String userName, String password) throws Exception {
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
            throw new Exception(
                    String.format("Cant get connection for model:%s user:%s password: %s",
                            database, userName, password),
                    e);
        }

    }

    @Override
    public void dropTable(String tableName) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS public. " + tableName);
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void insertRow(String tableName, DataSet input) throws Exception {
        try {
            Statement stmt = connection.createStatement();
            String ColumnNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");
            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + ColumnNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
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

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
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

    @Override
    public int updateTable(String tableName, String calumnForUpdate, String oldVelue, String newVelue) throws Exception {

        String sql = "UPDATE public." + tableName + " SET " + calumnForUpdate + "=?  WHERE " + calumnForUpdate + " = ?";
        int countChenges;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, oldVelue);
            pstmt.setString(2, newVelue);
            countChenges = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return countChenges;
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'");

            List<String> tableColumsName = new ArrayList<>();


            while (rs.next()) {
                tableColumsName.add( rs.getString("column_name"));
            }

            rs.close();
            stmt.close();
            return tableColumsName;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    @Override
    public List<String> getTableColumnsName(String tableName){
        List<String> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT * FROM public."+tableName);
            int columnsCount=rs1.getMetaData().getColumnCount();

            int index=0;
            while (rs1.next()){
            result.add(rs1.getString("column_name"));
            }
            stmt.close();



    } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    public List<String> ColumnNamesWithoutAvtoincrement(String tableName) throws Exception {
        {
            ArrayList<String> result = new ArrayList<>();
            try {
                Statement stmt = connection.createStatement();

                ResultSet rs1 = stmt.executeQuery("SELECT column_name  FROM information_schema.columns\n" +
                        "where  Table_schema = 'public' and column_name not in (\n" +
                        "  SELECT column_name  FROM information_schema.columns\n" +
                        "  where  Table_schema = 'public' and columns.column_default like '%nextval%'\n" +
                        ") AND table_name='"+tableName+"'");

                while (rs1.next()){
                    result.add(rs1.getString("column_name"));
                }
                stmt.close();




            } catch (SQLException e) {
                throw new Exception(e.getMessage());
            }
            return  result;
        }
    }

    @Override
    public boolean isConnected() {
        return connection!=null;
    }

    @Override
    public void exit() throws SQLException {

        connection.close();

    }

    @Override
    public int getTableSize(String tableName) throws Exception {
        String resultS;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + tableName);
            rs.next();
            resultS = rs.getString("count");
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        int result = Integer.parseInt(resultS);
        return result;
    }

    @Override
    public ArrayList<String> getTableNames() throws Exception {

        ArrayList<String> tablesName = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet tableNames = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            while (tableNames.next()) {
                tablesName.add(tableNames.getString("table_name"));
            }
            return tablesName;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

}
