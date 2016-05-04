package ua.com.juja.sqlcmd.model;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }

    @Test
    public void testGetAllTableNames() {
        Set<String> tablesNames = manager.getTablesNames();
        assertEquals("[user]", tablesNames.toString());
    }
}
