package ua.com.juja.sqlcmd.view;

import java.util.Scanner;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class Console implements Vive {

    @Override
    public void write(String massage) {
        System.out.println(massage);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}