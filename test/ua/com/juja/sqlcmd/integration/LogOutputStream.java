package ua.com.juja.sqlcmd.integration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public class LogOutputStream extends OutputStream{

    private String log;


    @Override
    public void write(int b) throws IOException {
        log += String.valueOf((char)b);
    }

    public String getLog() {
        return log;
    }
}
