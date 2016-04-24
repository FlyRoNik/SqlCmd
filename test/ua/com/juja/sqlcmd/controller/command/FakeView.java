package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class FakeView implements View {

    private String massages = "";
    private Object content;

    @Override
    public void write(String massage) {
        massages += massage + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    public Object getContent() {
        return massages;
    }
}
