package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager mamager = new JDBCDatabaseManager();

        MainController conroller = new MainController(view, mamager);
        conroller.run();
    }
}
