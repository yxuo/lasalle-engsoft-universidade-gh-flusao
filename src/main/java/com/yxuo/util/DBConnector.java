package com.yxuo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    
    private static final String URL = Config.getProperty("db.url");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void parseQuery(String query) {
        System.out.println(query);
    }
}
