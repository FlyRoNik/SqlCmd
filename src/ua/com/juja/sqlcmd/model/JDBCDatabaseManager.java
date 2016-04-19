package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by FlyRoNik on 09.04.2016.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e){
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    @Override
    public String[] getTablesNames() {
        try {
            String sql = "SELECT table_name " +
                    "FROM information_schema.tables " +
                    "WHERE table_schema='public' " +
                    "AND table_type='BASE TABLE'";
            String[] tables = new String[100];
            int index = 0;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            rs.close();
            stmt.close();
            return Arrays.copyOf(tables, index, String[].class);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project", e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database,
                    userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format(" Cant get connection for model:%s user:%s",
                            database, userName), e);
        }
    }

    @Override
    public void clear(String tableName) {
        try{
            String sql = "DELETE FROM public." + tableName;
            insert(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, DataSet input) {
        try{
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            insert("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String string = getNameFormated(newValue, "%s = ?,");

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE public." + tableName + " SET " + string + " WHERE id = ?");
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index++, value);
            }
            ps.setInt(index, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private void insert(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
}