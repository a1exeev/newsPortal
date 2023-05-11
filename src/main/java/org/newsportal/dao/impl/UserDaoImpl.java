package org.newsportal.dao.impl;

import org.newsportal.dao.UserDao;
import org.newsportal.dao.entity.User;
import org.newsportal.dao.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String CREATE_SQL = "INSERT INTO \"user\" (username, password) VALUES (?, ?)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE \"user\" SET username = ?, password = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM \"user\" WHERE id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM \"user\" WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM \"user\"";
    private static final String FIND_BY_ARTICLE_ID_SQL = "SELECT u.id, u.username, u.password " +
            "FROM \"user\" u " +
            "JOIN article a ON u.id = a.author_id " +
            "WHERE a.id = ?";
    private final ConnectionPool connectionPool;

    public UserDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(User user) {
        Connection connection = connectionPool.acquireConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public User findById(Long id) {
        User user = null;
        Connection connection = connectionPool.acquireConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
    }

    @Override
    public User findByArticleId(Long id) {
        User user = null;
        Connection connection = connectionPool.acquireConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ARTICLE_ID_SQL);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.acquireConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return users;
    }

    @Override
    public void updateById(User user, Long id) {
        Connection connection = connectionPool.acquireConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = connectionPool.acquireConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL);
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
