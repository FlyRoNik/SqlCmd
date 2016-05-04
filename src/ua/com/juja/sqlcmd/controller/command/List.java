package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Set;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public class List implements Command{

    private View view;
    private DatabaseManager manager;

    public List(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        Set<String> tablesNames = manager.getTablesNames();

        String massage = tablesNames.toString();

        view.write(massage);
    }
}
