package ua.com.juja.sqlcmd.model;

import java.util.*;

/**
 * Created by FlyRoNik on 17.04.2016.
 */
public class InMemoryDatabaseManager implements  DatabaseManager{

    public static final String TABLE_NAME = "user";

    private List<DataSet> data = new LinkedList<>();

    @Override
    public List<DataSet> getTableData(String tableName) {
        validateTable(tableName);
        return data;
    }

    @Override
    public int getSize(String tableName) {
        return data.size();
    }

    private void validateTable(String tableName) {
        if (!"user".equals(tableName)) {
            throw new UnsupportedOperationException("Only for 'user' table, but you try to work with: " + tableName);
        }
    }

    @Override
    public Set<String> getTablesNames() {
        return new LinkedHashSet<>(Collections.singletonList(TABLE_NAME));
    }

    @Override
    public void connect(String database, String userName, String password) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data.clear();
    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);
        data.add(input);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        validateTable(tableName);
        for (DataSet list : data) {
            if ((int) list.get("id") == id) {
                list.updateFrom(newValue);
            }
        }
    }

    @Override
    public Set<String> getTablesColumns(String tableName) {
        return new LinkedHashSet<>(Arrays.asList("name", "password", "id"));
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
