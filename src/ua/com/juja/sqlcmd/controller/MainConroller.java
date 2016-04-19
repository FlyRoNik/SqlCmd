package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class MainConroller {

    private View view;
    private DatabaseManager manager;

    public MainConroller(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run(){
        connectToDb();
        //
        //
        //
    }

    private void connectToDb() {
        view.write("Привет юзер!");
        view.write("Введите, пожалуйста имя базы данных, " +
                "имя пользователя и пароль в формате: database|userName|password");

        while (true){
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных " +
                            "знаком '|', ожидается 3, но есть: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
        }


        view.write("Успех!");
    }

    private void printError(Exception e) {
        String massage = e.getMessage();
        if (e.getCause() != null) {
            massage += " " + e.getCause().getMessage();
        }
        view.write("Неудача! по причине: " + massage);
        view.write("Повтори попытку!");
    }
}
