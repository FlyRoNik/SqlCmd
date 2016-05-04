package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        Set<String> tablesNames = manager.getTablesNames();
        assertEquals("[user, test]", tablesNames.toString());
    }

    @Test
    public void testGetTableData() {
        //given
        manager.clear("user");

        //when
        DataSet input = new DataSetImpl();
        input.put("name", "Nikita");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //then
        List<DataSet> users = manager.getTableData("user");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Nikita, pass, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("user");

        //when
        DataSet input = new DataSetImpl();
        input.put("name", "Nikita");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("user", input);

        //when
        DataSet newValue = new DataSetImpl();
        newValue.put("password", "pass2");
        manager.update("user", 13, newValue);

        //then
        List<DataSet> users = manager.getTableData("user");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Nikita, pass2, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("user");

        //when
        Set<String> columnNames = manager.getTablesColumns("user");

        //then
        assertEquals("[name, password, id]", columnNames.toString());
    }

    @Test
    public void testIsConnected() {
        //given
        //when
        //then
        assertTrue(manager.isConnected());
    }
}
