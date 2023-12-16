package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/katapp114?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "password";

        return DriverManager.getConnection(url, username, password);
    }
}
