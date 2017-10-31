package main.java.Sqlcmd.model;

import java.sql.*;
import java.util.*;

/**
 * Created by Kirill on 09.07.2017.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    public Connection connection;


    @Override
    public ArrayList<DataSet> getTableData(String tableName) {
    ArrayList<DataSet> resultDataSet = null;
    try(Statement stmt=connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);)    {
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
        e.printStackTrace();
    }
        return resultDataSet;
    }

    @Override
    public boolean createTable(String tableName, List<String> input, int offset) throws SQLException {
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

        }


        return saccses;
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

    // todo . wrong implementation of the method. fix and criate new.
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
    public void deleteTable(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("DROP TABLE IF EXISTS public. " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void create(String tableName, DataSet input) throws Exception {
        boolean saccses = false;
        int flag;
        try {
            Statement stmt = connection.createStatement();

            String ColumnNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            flag = stmt.executeUpdate("INSERT INTO public." + tableName + " (" + ColumnNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
            if (flag > 0) {
                saccses = true;
            }

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
    public String[] getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'");

            String[] tables = new String[100];

            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public  String[] getTableColumnsName(String tableName){
        String[] res;
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT * FROM public."+tableName);
            int columnsCount=rs1.getMetaData().getColumnCount();
            res= new String[columnsCount];
            int index=0;
            for (int i = 0; i <columnsCount ; i++) {
                res[index++] =(String) rs1.getMetaData().getColumnName(i+1);
            }
            stmt.close();



    } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
        return res;
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
    public String[] getTableNames() throws Exception {

        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet tableNames = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            while (tableNames.next()) {
                tables.add(tableNames.getString("table_name"));
            }
            return tables.toArray(new String[tables.size()]);
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }


    //decide to remove todo
    private int getResultSetRowCount(ResultSet rs) {
        int size = 0;
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(SQLException ex) {
            return 0;
        }
        return size;
    }

//    @Override
//    public boolean insert(String tableName, DataSet input) throws Exception {
//        boolean flag=false;
//
//        try (Statement stm=connection.createStatement()) {
//            String sql = "INSERT INTO public." + tableName + " VALUES (DEFAULT";
////            int index=0;
//            for (int i = 0; i < input.getSize(); i++) {
//                sql = sql + "," + input.data[i].getValue();  // BAG todo
////                index++;
//            }
//            flag = stm.execute(sql + " )");
//        }catch (SQLException e){
//            e.printStackTrace();
//            throw new Exception("что-то пошло не так");
//        }
//        return flag;
//    }


}
