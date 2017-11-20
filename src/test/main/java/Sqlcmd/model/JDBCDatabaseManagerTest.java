package main.java.Sqlcmd.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Kirill on 16.07.2017.
 */
public class JDBCDatabaseManagerTest {
    private static JDBCDatabaseManager manager = new JDBCDatabaseManager();
    private static String tableName = "testtable";

    @BeforeClass
    public static void setUp() throws Exception {
        manager.connect("testSQLcmd", "postgres", "123456");
        String[] ColumnName = {"column1", "column2"};
        manager.createTable(tableName, Arrays.asList(ColumnName), 15);

        DataSet ds = new DataSet();
        ds.put("column1", "aaa");
        ds.put("column2", "bbb");
        manager.insertRow(tableName, ds);
    }

    @AfterClass
    public static void setOUT() throws Exception {

        manager.dropTable("testtable");
        manager.dropTable("testt");
        if (manager.isConnected()) {
            manager.connection.close();
        }
    }

    @Test
    public void getTableData() throws Exception {
        ArrayList<DataSet> actuall = manager.getTableData(tableName);
        assertEquals(1, manager.getTableSize(tableName));
        for (DataSet dst : actuall) {
            assertEquals("[1, aaa, bbb]", dst.ValuesToString());
            assertEquals("[id_testtable, column1, column2]", dst.NamesToString());
        }
    }

    @Test
    public void createTable() throws Exception {
        String tableName = "TestT";
        manager.dropTable(tableName);
        ArrayList<String> columnName = new ArrayList();
        columnName.add("column1");
        columnName.add("column2");
        manager.createTable(tableName, columnName, 15);
        ArrayList<String> actual = manager.getTableNames();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("users");
        expected.add("account");
        expected.add("testtable");
        expected.add(tableName.toLowerCase());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void connect() throws Exception {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        boolean[] expected = new boolean[]{false, true};
        boolean[] actual = new boolean[2];
        actual[0] = manager.isConnected();
        manager.connect("testSQLcmd", "postgres", "123456");
        actual[1] = manager.isConnected();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void dropTable() throws Exception {
        String tableName = "TestDT";
        ArrayList<String> columnName = new ArrayList();
        columnName.add("column1");
        columnName.add("column2");
        manager.createTable(tableName, columnName, 15);
        manager.dropTable(tableName);
        ArrayList actual = manager.getTableNames();
        assertFalse(actual.contains(tableName));
    }

    @Test
    public void insertRow() throws Exception {
        int sizeBefore = manager.getTableSize(tableName);
        DataSet ds = new DataSet();
        ds.put("column1", "bbb");
        ds.put("column2", "ccc");
        manager.insertRow(tableName, ds);
        int sizeAfter = manager.getTableSize(tableName);
        assertTrue((sizeAfter - sizeBefore) == 1);


    }

    @Test
    public void update() throws Exception {
        manager.updateTable(tableName, "column1", "FFF", "aaa");
        ArrayList<DataSet> actuall = manager.getTableData(tableName);
        for (DataSet dst : actuall) {
            assertEquals("[1, FFF, bbb]", dst.ValuesToString());
        }
    }

    @Test
    public void updateTable() throws Exception {
        manager.updateTable(tableName, "column2", "FFF", "bbb");
        ArrayList<DataSet> actuall = manager.getTableData(tableName);
        for (DataSet dst : actuall) {
            assertEquals("[1, FFF, FFF]", dst.ValuesToString());
        }
    }

    @Test
    public void getTableColumns() throws Exception {

        String tableName = "users";
        String[] expected = {"id_user", "user_name", "user_password"};
        String[] actual = manager.getTableColumnsName(tableName);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getTableColumnsName() throws Exception {
        String tableName = "users";
        String[] expected = {"id_user", "user_name", "user_password"};
        String[] actual = manager.getTableColumnsName(tableName);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void columnNamesWithoutAvtoincrement() throws Exception {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        manager.connect("testSQLcmd", "postgres", "123456");
        String tableName = "users";
        List<String> expected = new ArrayList<>();
        expected.add("user_name");
        expected.add("user_password");

        List<String> actual = manager.ColumnNamesWithoutAvtoincrement(tableName);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void isConnected() throws Exception {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        boolean[] expected = new boolean[]{false, true};
        boolean[] actual = new boolean[2];
        actual[0] = manager.isConnected();
        manager.connect("testSQLcmd", "postgres", "123456");
        actual[1] = manager.isConnected();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getTableNames() throws Exception {
        ArrayList expected = new ArrayList();
        expected.add("users");
        expected.add("account");
        expected.add("testtable");
        ArrayList actual = manager.getTableNames();
        assertTrue(actual.containsAll(expected) & expected.containsAll(actual));
//        assertEquals(expected, actual);
    }

    @Test
    public void getTableSize() throws Exception {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        manager.connect("testSQLcmd", "postgres", "123456");
        int actual = manager.getTableSize("users");
        int expected = 15;
        assertEquals(expected, actual);
    }

}