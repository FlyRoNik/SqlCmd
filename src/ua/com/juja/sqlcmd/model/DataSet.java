package ua.com.juja.sqlcmd.model;

/**
 * Created by FlyRoNik on 04.05.2016.
 */
public interface DataSet {
    void put(String name, Object value);

    Object[] getValues();

    String[] getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
