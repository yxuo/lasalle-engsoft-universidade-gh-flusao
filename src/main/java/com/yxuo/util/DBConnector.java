package com.yxuo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = Config.get("db.url");
    private static final String username = Config.getOrDefault("db.username", null);
    private static final String password = Config.getOrDefault("db.password", null);

    public static Connection getConnection() throws SQLException {
        if (URL.contains("sqlite")) {
            return DriverManager.getConnection(URL);
        } else {
            return DriverManager.getConnection(URL, username, password);
        }
    }

    public static void printQuery(String query) {
        System.out.println(query);
    }
}
