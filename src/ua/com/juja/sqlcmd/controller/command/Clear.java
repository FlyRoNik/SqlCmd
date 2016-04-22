package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 22.04.2016.
 */
public class Clear implements Command {
    private View view;
    private DatabaseManager manager;

    public Clear(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw  new IllegalArgumentException("Формат команды 'clear|tableName'," +
                    " а ты ввел: " + command);
        }
        manager.clear(data[1]);

        view.write(String.format("Таблица %s была успешно очищена.", data[1]));
    }
}
