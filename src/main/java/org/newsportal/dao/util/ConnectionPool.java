package org.newsportal.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://localhost:16162/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final int POOL_SIZE = 10;

    private final BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(POOL_SIZE);
    private static ConnectionPool instance;

    private ConnectionPool() {
        try {
            Class.forName(DRIVER);
            for (int i = 0; i < POOL_SIZE; i++) {
                connections.add(createConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized Connection acquireConnection() {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.createConnection()) {
            System.out.println(connection.getMetaData().getURL());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
