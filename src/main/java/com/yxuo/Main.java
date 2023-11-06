package com.yxuo;

import java.sql.SQLException;

import com.yxuo.util.Config;
import com.yxuo.util.DBConnector;
import com.yxuo.view.cli.MainCliView;

public class Main {

    public static final Config config = Config.getInstance();

    public static void testConnection() throws SQLException {
        try {
            DBConnector.getConnection();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static void main(String[] args) throws SQLException {

        testConnection();
        MainCliView view = new MainCliView();
        view.display();

    }
}
