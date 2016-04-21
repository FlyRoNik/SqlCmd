package ua.com.juja.sqlcmd.controller.command;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public interface Command {

    boolean canProcess(String command);

    void process(String command);

}
