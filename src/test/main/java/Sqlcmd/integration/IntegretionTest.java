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


public class IntegretionTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                        // exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n", getData());
    }

    @Test
    public void testConnect() {
//        given
        in.add("connect|testSQLcmd|postgree|123456");
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("exit");
//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                wrong connect
                        "Подключение не состоялось! Проверте правельность указания параметров.\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                correct connect
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n", getData());
    }

    @Test
    public void testList() {
//        given
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("list");
        in.add("exit");
//        when
        Main.main(new String[0]);
//        Then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
//                list
                "users, account\r\n" +
                "Введи команду (или help для помощи):\r\n" +
//                exit
                "Закрываю работу служб: \r\n" +
                "_______________________ ok\r\n", getData());
    }

    @Test
    public void testFind() {
        //        given
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("find");
        in.add("find|tttt");
        in.add("find|users");
        in.add("exit");
//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                  connect|testSQLcmd|postgres|123456
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                  find
                        "неправельный формат команды 'find', А Должно быть 'find|tableName '.\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                  find|tttt
                        "Таблица с именем 'tttt' не найдена в базе.\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                  find|users
                        "|id_user        |user_name      |user_password  |\r\n" +
                        "________________________________________________\r\n" +
                        "| 1             | kit           | pass          |\r\n" +
                        "________________________________________________\r\n" +
                        "| 2             | kirill        | pass          |\r\n" +
                        "________________________________________________\r\n" +
                        "| 3             | alla          | pass          |\r\n" +
                        "________________________________________________\r\n" +
                        "| 29            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 30            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 31            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 32            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 33            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 34            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 35            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 36            | Zara          | sdfsdfsadf    |\r\n" +
                        "________________________________________________\r\n" +
                        "| 37            | fgdjl         | skdjfh        |\r\n" +
                        "________________________________________________\r\n" +
                        "| 41            | kir           | ddddd         |\r\n" +
                        "________________________________________________\r\n" +
                        "| 42            | kir           | ddddd         |\r\n" +
                        "________________________________________________\r\n" +
                        "| 43            | git           | hab           |\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//         exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n"

                , getData());

    }

    @Test
    public void testUnsupported() {
//        given
        in.add("connect|testSQLcmd|postgres|123456");
        // in.add("кракозябла");
        in.add("crakozabla");
        in.add("exit");
//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                wrong command - crakozabla
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "\tКоманды 'crakozabla' не существует\r\n" +
//                correct command crakozabla
//                exit
                        "Введи команду (или help для помощи):\r\n" +
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n",
                getData());


    }

    @Test
    public void testCreateTable() {
        //        given
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("CreateTable|t|tt|ttt|ert");
        in.add("droptable|t");
        in.add("exit");

//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                  connect|testSQLcmd|postgres|123456
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                       CreateTable|kkk|1|2|3
                        "Успех! Таблица t успешно добавленна. вот ее поля \r\n" +
                        "|tt             |ttt            |ert            |\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        // "|"+
                        "Таблица t  была успешно удалена. \r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//         exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n"

                , getData());


    }

    @Test
    public void testDropTable() {
        //        given
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("CreateTable|t|tt|ttt|ert");
        in.add("droptable|t");
        in.add("exit");

//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                  connect|testSQLcmd|postgres|123456
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                       CreateTable|kkk|1|2|3
                        "Успех! Таблица t успешно добавленна. вот ее поля \r\n" +
                        "|tt             |ttt            |ert            |\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        // "|"+
                        "Таблица t  была успешно удалена. \r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//         exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n"

                , getData());


    }

    @Test
    public void testTableSize() {
        //        given
        in.add("connect|testSQLcmd|postgres|123456");
        in.add("tablesize|users");
        //in.add("droptable|t");
        in.add("exit");

//        when
        Main.main(new String[0]);
//        Then
        assertEquals(
                "Привет юзер!\r\n" +
                        "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
//                  connect|testSQLcmd|postgres|123456
                        "Успех!\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                  tablesize|users
                        "Таблица users содержит 15 записей.\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
//                  exit
                        "Закрываю работу служб: \r\n" +
                        "_______________________ ok\r\n"
                , getData());


    }

}
