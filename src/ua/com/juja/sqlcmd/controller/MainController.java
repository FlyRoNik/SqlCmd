package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 19.04.2016.
 */
public class MainController {

    private Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(view, manager),
                new Exit(view),
                new Help(view),
                new IsConnected(view, manager),
                new List(view, manager),
                new Clear(view, manager),
                new Create(view, manager),
                new Find(view, manager),
                new Unsupported(view)};
    }

    public void run(){
        try{
            doWork();
        } catch (ExitException e){
            //do nothing
        }
    }

    private void doWork() {
        view.write("Привет юзер!");
        view.write("Введите, пожалуйста имя базы данных, " +
            "имя пользователя и пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();
            if (input == null) {  //null is close application
                new Exit(view).process(input);
            }
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("Введи команду (или help для помощи):");
        }
    }
}
