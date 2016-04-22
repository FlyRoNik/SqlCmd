package ua.com.juja.sqlcmd.model;

/**
 * Created by FlyRoNik on 09.04.2016.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest{

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
