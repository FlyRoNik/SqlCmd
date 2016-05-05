package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Set;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public class Tables implements Command{

    private View view;
    private DatabaseManager manager;

    public Tables(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        Set<String> tablesNames = manager.getTablesNames();

        String massage = tablesNames.toString();

        view.write(massage);
    }
}
