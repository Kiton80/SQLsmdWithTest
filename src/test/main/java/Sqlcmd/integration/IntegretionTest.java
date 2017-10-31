package main.java.Sqlcmd.integration;

import main.java.Sqlcmd.controller.Main;
import main.java.Sqlcmd.model.DatabaseManager;
import main.java.Sqlcmd.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kirill on 25.10.2017.
 */
public class IntegretionTest {

//    public ConfigurableInputStream in;
//    private ByteArrayOutputStream out;
//    private DatabaseManager databaseManager;
//
//    @Before
//    public void setup() {
//        databaseManager = new JDBCDatabaseManager();
//        out = new ByteArrayOutputStream();
//        in = new ConfigurableInputStream();
//
//        System.setIn(in);
//        System.setOut(new PrintStream(out));
//    }
//
//    public String getData() {
//        try {
//            String result = new String(out.toByteArray(), "UTF-8");
//            out.reset();
//            return result;
//        } catch (UnsupportedEncodingException e) {
//            return e.getMessage();
//        }
//    }
//
//    @Test
//    public void testExit() {
//        // given
//        in.add("exit");
//
//        // when
//        Main.main(new String[0]);
//
//        // then
//        assertEquals(
//                "Привет юзер!\r\n" +
//                         "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                // exit
//                         "Закрываю работу служб: \r\n" +
//                         "_______________________ ok\r\n", getData());
//    }

}
