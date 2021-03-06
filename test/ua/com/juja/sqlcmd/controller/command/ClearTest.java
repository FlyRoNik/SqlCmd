package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(view, manager);
    }

    @Test
    public void testClearTable() {
        //given

        //when
        command.process("clear|people");

        //then
        verify(manager).clear("people");
        verify(view).write("Таблица people была успешно очищена.");
    }

    @Test
    public void testCanProcessClearWithParametersString() {
        //give

        //when
        boolean canProcess = command.canProcess("clear|people");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        //give

        //when
        boolean canProcess = command.canProcess("clear");

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
    public void testValidationErrorWhenCountParametersIsLessThen2() {
        //give

        //when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear",
                    e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThen2() {
        //give

        //when

        try {
            command.process("clear|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear|table|qwe",
                    e.getMessage());
        }
    }
}
