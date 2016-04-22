package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 22.04.2016.
 */
public class Create implements Command {


    private View view;
    private DatabaseManager manager;

    public Create(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Должно быть четное количество параметров " +
                    "в формате 'create|tableName|column1|value1|column2|value2|...|columnN|valueN|' " +
                    ", а ты преслал: " + command);
        }

        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName,value);
        }
        manager.create(tableName, dataSet);

        view.write(String.format("Запись %s была успешно созданна в таблице '%s'.", dataSet, tableName));
    }
}
