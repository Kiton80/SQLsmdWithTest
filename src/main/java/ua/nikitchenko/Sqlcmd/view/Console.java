package main.java.Sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by indigo on 25.08.2015.
 */
public class Console implements View {
    String masedg="";
    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
