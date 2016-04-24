package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by FlyRoNik on 24.04.2016.
 */
public class FakeView implements View {

    private String massages = "";
    private String input = null;

    @Override
    public void write(String massage) {
        massages += massage + "\n";
    }

    @Override
    public String read() {
        if (input == null) {
            throw new IllegalStateException("Для работы проинициализируйте метод read()");
        }
        String result = input;
        this.input = null;
        return result;
    }

    public void addRead(String input) {
        this.input = input;
    }

    public Object getContent() {
        return massages;
    }
}
