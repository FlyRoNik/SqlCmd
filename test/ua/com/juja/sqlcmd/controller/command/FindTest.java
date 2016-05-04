package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(view, manager);
    }

    @Test
    public void testPrintTableData() {
        //given
        setupTableColumns("people", "id", "name", "surname", "age");

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);
        user1.put("name", "Nikita");
        user1.put("surname", "Frolov");
        user1.put("age", 20);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);
        user2.put("name", "Julia");
        user2.put("surname", "Norkina");
        user2.put("age", 19);

        when(manager.getTableData("people"))
                .thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|people");

        //then
        shouldPrint("[--------------------, " +
                "|id|name|surname|age|, " +
                "--------------------, " +
                "|12|Nikita|Frolov|20|, " +
                "|13|Julia|Norkina|19|, " +
                "--------------------]");
    }

    private void setupTableColumns(String tableName, String... columns) {
        when(manager.getTablesColumns(tableName))
                .thenReturn(new LinkedHashSet<>(Arrays.asList(columns)));
    }

    @Test
    public void testCanProcessFindWithParametersString() {
        //give

        //when
        boolean canProcess = command.canProcess("find|people");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessFindWithoutParametersString() {
        //give

        //when
        boolean canProcess = command.canProcess("find");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        //give

        //when
        boolean canProcess = command.canProcess("qwe|people");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        //given
        setupTableColumns("people", "id", "name", "surname", "age");

        when(manager.getTableData("people"))
                .thenReturn(new LinkedList<>());

        //when
        command.process("find|people");

        //then

        shouldPrint("[--------------------, " +
                "|id|name|surname|age|, " +
                "--------------------, " +
                "--------------------]");
    }

    @Test
    public void testPrintTableDataWithOneColumn() {
        //given
        setupTableColumns("test", "id");

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);

        when(manager.getTableData("test"))
                .thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|test");

        //then
        shouldPrint("[--------------------, " +
                "|id|, " +
                "--------------------, " +
                "|12|, " +
                "|13|, " +
                "--------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
