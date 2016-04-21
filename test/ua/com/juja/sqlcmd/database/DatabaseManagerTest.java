package ua.com.juja.sqlcmd.database;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by FlyRoNik on 09.04.2016.
 */
public abstract class DatabaseManagerTest {

    protected DatabaseManager manager;

    public abstract DatabaseManager getDatabaseManager();

    @Before
    public void setup(){
        manager = getDatabaseManager();
        manager.connect("sqlcmd", "postgres", "123");
        manager.clear("user");
    }

    @Test
    public void testGetAllTableNames() {
        String[] tablesNames = manager.getTablesNames();
        assertEquals("[user, test]", Arrays.toString(tablesNames));
    }

    @Test
    public void testGetTableData() {
        //given
        manager.clear("user");

        //when
        DataSet input = new DataSet();
        input.put("name", "Nikita");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //then
        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Nikita, pass, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("user");

        //when
        DataSet input = new DataSet();
        input.put("name", "Nikita");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        manager.update("user", 13, newValue);

        //then
        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Nikita, pass2, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("user");

        //when
        String[] columnNames = manager.getTablesColumns("user");

        //then
        assertEquals("[name, password, id]", Arrays.toString(columnNames));
    }

    @Test
    public void testIsConnected() {
        //given
        //when
        //then
        assertTrue(manager.isConnected());
    }
}
