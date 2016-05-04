package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by FlyRoNik on 09.04.2016.
 */
public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public List<DataSet> getTableData(String tableName) {
        int size = getSize(tableName);
        List<DataSet> result = new ArrayList<>(size);
//        List<DataSet> result = new LinkedList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName))
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                }
            }
            return result;
        } catch (SQLException e){
            e.printStackTrace();
            return result;
        }
    }

    private int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName))
        {
            rsCount.next();
            return rsCount.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Set<String> getTablesNames() {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name " +
                    "FROM information_schema.tables " +
                    "WHERE table_schema='public' " +
                    "AND table_type='BASE TABLE'"))
        {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
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
            if (connection != null) {
                connection.close();
            }
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
        String sql = "DELETE FROM public." + tableName;
        insert(sql);
    }

    @Override
    public void create(String tableName, DataSet input) {
        String tableNames = getNameFormated(input, "%s,");
        String values = getValuesFormated(input, "'%s',");

        insert("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                "VALUES (" + values + ")");
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
            String string = getNameFormated(newValue, "%s = ?,");

        try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE public." + tableName + " SET " + string + " WHERE id = ?"))
        {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index++, value);
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTablesColumns(String tableName) {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                    "FROM information_schema.columns " +
                    "WHERE table_schema = 'public' " +
                    "AND table_name = '" + tableName + "'"))
        {
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
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

    private void insert(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}