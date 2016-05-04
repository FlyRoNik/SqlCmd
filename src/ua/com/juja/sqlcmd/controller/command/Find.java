package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.List;
import java.util.Set;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public class Find implements Command {

    private View view;
    private DatabaseManager manager;

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1]; // TODO to add validation

        List<DataSet> tableData = manager.getTableData(tableName);
        Set<String> tableColumns = manager.getTablesColumns(tableName);

        printHeader(tableColumns);
        printTable(tableData);
    }

    private void printTable(List<DataSet> tableData) {

        tableData.forEach(this::printColumn);
        view.write("--------------------");
    }

    private void printHeader(Set<String> tableData) {
        String result = "|";
        for (String name : tableData) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void printColumn(DataSet column) {
        Object[] values = column.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }
}
