package ua.com.juja.sqlcmd.model;

import java.util.*;

/**
 * Created by FlyRoNik on 17.04.2016.
 */
public class InMemoryDatabaseManager implements  DatabaseManager{

    private Map<String, List<DataSet>> tables =  new LinkedHashMap<>();

    @Override
    public List<DataSet> getTableData(String tableName) {
        return get(tableName);
    }

    @Override
    public int getSize(String tableName) {
        return tables.get(tableName).size();
    }

    @Override
    public Set<String> getTablesNames() {
        return tables.keySet();
    }

    @Override
    public void connect(String database, String userName, String password) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        get(tableName).clear();
    }

    private List<DataSet> get(String tableName) {
        if (!tables.containsKey(tableName)) {
            tables.put(tableName, new LinkedList<>());
        }
        return tables.get(tableName);
    }

    @Override
    public void create(String tableName, DataSet input) {
        get(tableName).add(input);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (DataSet dataSet : get(tableName)) {
            if ((int) dataSet.get("id") == id) {
                dataSet.updateFrom(newValue);
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
