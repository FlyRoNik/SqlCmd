package ua.com.juja.sqlcmd.model;

import java.util.List;
import java.util.Set;

/**
 * Created by FlyRoNik on 10.04.2016.
 */
public interface DatabaseManager {
    List<DataSet> getTableData(String tableName);

    int getSize(String tableName);

    Set<String> getTablesNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTablesColumns(String tableName);

    boolean isConnected();
}
