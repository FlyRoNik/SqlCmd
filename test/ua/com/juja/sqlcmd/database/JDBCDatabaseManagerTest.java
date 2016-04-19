package ua.com.juja.sqlcmd.database;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

/**
 * Created by FlyRoNik on 09.04.2016.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest{

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
