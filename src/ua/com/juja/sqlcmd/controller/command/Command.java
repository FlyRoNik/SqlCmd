package ua.com.juja.sqlcmd.controller.command;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public interface Command {

    boolean canProcess(String command);

    void process(String command);

//    TODO выделить новым методом интерфейса Command формат команды и описание, которое выводит help
//    String format();
//    String description();
}
