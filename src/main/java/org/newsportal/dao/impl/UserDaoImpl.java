package org.newsportal.dao.impl;

import org.newsportal.dao.UserDao;
import org.newsportal.dao.entity.User;
import org.newsportal.dao.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {
    private static final String SQL_CREATE = "INSERT INTO user_account (email, password, firstname, lastname, role) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_READ = "SELECT * FROM user WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE SET email = ?, password = ?, firstName = ?, lastName = ?, role = ? WHERE id = ?";
    private final ConnectionPool connectionPool;

    public UserDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User create(Connection connection, User user) {
        try {
            connection = connectionPool.acquireConnection();

            try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getLastName());
                statement.setString(5, user.getRole());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return user;
    }

    @Override
    public User read(Connection connection, int id) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setRole(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User update(Connection connection, User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {


        }


        return ;
    }
}
