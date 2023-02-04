package com.JHF.Singletones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {
    private Set<Connection> connections = new HashSet<>();
    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/systemdb";
        String user = "root";
        String password = "root";
        Connection conn = null;
        for (int i = 0; i < 10; i++) {

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connections.add(conn);
        }

    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        Connection conn = null;
        if(connections.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            Iterator it = connections.iterator();
            conn = (Connection) it.next();
            connections.remove(conn);
    }
        return conn;
    }

    public void restoreConnection(Connection connection) {
        connections.add(connection);
    }

    public void closeAllConnections() {
        for (Connection conn : connections) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
