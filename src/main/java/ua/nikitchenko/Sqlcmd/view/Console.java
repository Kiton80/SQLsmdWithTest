package ua.nikitchenko.Sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;


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
