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

    @BeforeClass
    public static void setUp() throws Exception {
        manager.connect("testSQLcmd", "postgres", "123456");
        String[] ColumnName = {"column1", "column2"};
         manager.createTable("testtable", Arrays.asList(ColumnName),15);

    }

    @AfterClass
    public static void setOUT() throws Exception {
        manager.dropTable("testtable");
        if (manager.isConnected()) {
            manager.connection.close();
        }
    }

    @Test
    public void getTableData() throws Exception {
    }

    @Test
    public void createTable() throws Exception {
        String tableName = "TestT";
        ArrayList<String> columnName = new ArrayList();
        columnName.add("column1");
        columnName.add("column2");
        manager.createTable("TestT", columnName, 15);
        ArrayList<String> actual = manager.getTableNames();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("users");
        expected.add("account");
        expected.add("testtable");
        expected.add("testt");
        assertEquals(expected, actual);
        manager.dropTable(tableName);
    }

    @Test
    public void connect() throws Exception {
        //todo
    }

    @Test
    public void dropTable() throws Exception {
        String tableName = "TestDT";
        ArrayList<String> columnName = new ArrayList();
        columnName.add("column1");
        columnName.add("column2");
        manager.createTable(tableName, columnName, 15);
        manager.dropTable(tableName);
        ArrayList expected = new ArrayList();
        expected.add("users");
        expected.add("account");
        expected.add("testtable");
        ArrayList actual = manager.getTableNames();
        assertEquals(expected, actual);
    }

    @Test
    public void insertRow() throws Exception {
// todo
    }

    @Test
    public void update() throws Exception {
//        todo
    }

    @Test
    public void updateTable() throws Exception {
//        todo
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
    public void exit() throws Exception {
    }

    @Test
    public void getTableNames() throws Exception {
        ArrayList expected = new ArrayList();
        expected.add("users");
        expected.add("account");
        expected.add("testtable");
        ArrayList actual = manager.getTableNames();
        assertEquals(expected, actual);
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