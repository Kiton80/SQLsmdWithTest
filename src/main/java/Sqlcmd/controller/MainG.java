package main.java.Sqlcmd.controller;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Kirill on 25.06.2017.
 *
 *
 * db  //    connection = DriverManager.getConnection(
 "jdbc:postgresql://127.0.0.1:5432/testSQLcmd", "postgres",
 "123456");
 */
public class MainG {
    Connection connection ;

    public MainG(String DB_name,String DB_user,String DB_password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection(DB_name, DB_user, DB_password);



        this.connection = connection;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String DB_name="testSQLcmd";
        String DB_user="postgres";
        String DB_password="123456";
        String sql;

        Scanner scanner = new Scanner(System.in);
        String in = "n";
        System.out.println("Привет Юзер!!!");
        System.out.println("Войди пожалуйста в базу данных");
//        System.out.println("Для этого введи команду в формате: connect|name_database|user|password ");
//
//        in = scanner.nextLine();
//        String[] arrayIN = in.split("|");


        Class.forName("org.postgresql.Driver");


        Connection connection = getConnection(DB_name, DB_user, DB_password);

        Statement stmt = connection.createStatement();

         sql = "INSERT INTO users " + "VALUES (DEFAULT , 'Zara','sdfsdfsadf')";
        Insert(sql, stmt);



        sql = "SELECT * FROM users WHERE id_user >2";
        select(sql, connection);


        sql = "DELETE FROM users WHERE id_user<20 AND id_user>9";
        Delete(sql, connection);

        sql = "UPDATE users SET user_password=? WHERE id_user>3 ";
        update(sql, connection);


    }

    private static Connection getConnection(String DB_name, String DB_user, String DB_password) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + DB_name, DB_user,
        DB_password);
    }

    private static void Delete(String sql, Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        // pstmt.setInt(1, 5);
        pstmt.executeUpdate();
    }


    private static void update(String sql, Connection connection) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(sql);
        String str =""+ new Random().nextInt();
        pst.setInt(1, Integer.parseInt(str));
        pst.executeUpdate();
    }

    private static void select(String sql, Connection connection) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
        {
           System.out.print("id: "+rs.getString(1));
           System.out.print("  name: "+rs.getString(2));
           System.out.println("  password: "+rs.getString(3));
        }
        rs.close();
        st.close();
    }

    private static void Insert(String sql, Statement stmt) throws SQLException {
        stmt.executeUpdate(sql);
    }
}
