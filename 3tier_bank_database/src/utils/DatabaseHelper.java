package utils;

import org.postgresql.Driver;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper<T> {
    private final String jdbcURL;
    private final String username;
    private final String password;

    public DatabaseHelper(String jdbcURL, String username, String password) {
        this.jdbcURL = jdbcURL;
        this.username = username;
        this.password = password;
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseHelper(String jdbcURL) {
        this(jdbcURL, null, null);
    }

    private Connection getConnection() throws SQLException {
        if (username == null) {
            return DriverManager.getConnection(jdbcURL);
        } else {
            return DriverManager.getConnection(jdbcURL, username, password);
        }
    }

    private PreparedStatement prepare(Connection connection, String sql, Object[] parameters) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            stat.setObject(i + 1, parameters[i]);
        }
        return stat;
    }

    public int executeUpdate(String sql, Object... parameters) throws RemoteException {
        try (Connection connection = getConnection()) {
            PreparedStatement stat = prepare(connection, sql, parameters);
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    public T mapSingle(DataMapper<T> mapper, String sql, Object... parameters) throws RemoteException {
        try (Connection connection = getConnection()) {
            try(PreparedStatement stat = prepare(connection, sql, parameters)) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        return mapper.create(rs);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    public List<T> map(DataMapper<T> mapper, String sql, Object... parameters) throws RemoteException {
        try (Connection connection = getConnection()) {
            PreparedStatement stat = prepare(connection, sql, parameters);
            LinkedList<T> all;
            try (ResultSet rs = stat.executeQuery()) {
                all = new LinkedList<>();
                while (rs.next()) {
                    all.add(mapper.create(rs));
                }
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
}
