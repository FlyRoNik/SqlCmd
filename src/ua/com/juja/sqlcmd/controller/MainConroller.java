package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.Vive;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class MainConroller {
    public static void main(String[] args) {
        Vive vive = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();


        vive.write("Привет юзер!");
        vive.write("Введите, пожалуйста имя базы данных, " +
                "имя пользователя и пароль в формате: database|userName|password");

        while (true){
            String string = vive.read();
            String[] data = string.split("\\|");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
            try {
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                String massage = e.getMessage();
                if (e.getCause() != null) {
                    massage += " " + e.getCause().getMessage();
                }
                vive.write("Неудача! по причине: " + massage);
                vive.write("Повтори попытку!");
            }
        }



        vive.write("Успех!");
    }
}
