package ua.com.juja.sqlcmd.model;

import org.junit.Test;

import java.util.Arrays;

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
        String[] tablesNames = manager.getTablesNames();
        assertEquals("[user]", Arrays.toString(tablesNames));
    }
}
