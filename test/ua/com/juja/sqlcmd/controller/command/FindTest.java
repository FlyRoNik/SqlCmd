package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class FindTest {

    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
    }

    @Test
    public void testPrintTableData() {
        //given
        Command command = new Find(view, manager);
        when(manager.getTablesColumns("people"))
                .thenReturn(new String[]{"id", "name", "surname", "age"});

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Nikita");
        user1.put("surname", "Frolov");
        user1.put("age", 20);

        DataSet user2 = new DataSet();
        user2.put("id", 13);
        user2.put("name", "Julia");
        user2.put("surname", "Norkina");
        user2.put("age", 19);

        DataSet[] data = new DataSet[] {user1, user2};

        when(manager.getTableData("people"))
                .thenReturn(data);

        //when
        command.process("find|people");

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[--------------------, " +
                    "|id|name|surname|age|, " +
                    "--------------------, " +
                    "|12|Nikita|Frolov|20|, " +
                    "|13|Julia|Norkina|19|, " +
                    "--------------------]",
                captor.getAllValues().toString());
    }
}
