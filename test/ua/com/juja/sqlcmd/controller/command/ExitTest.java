package ua.com.juja.sqlcmd.controller.command;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class ExitTest {

    private FakeView view = new FakeView();

    @Test
    public void testCanProcessExitString() {
        //give
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("exit");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessQweString() {
        //give
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("qwe");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessExitCommand_throwsExitException() {
        //give
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            //do nothing
        }

        //then
        assertEquals("До скорой встречи!\n", view.getContent());
        //throws ExitException
    }
}
