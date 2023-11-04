package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yxuo.util.DBConnector;

public abstract class BaseRepository {

    public abstract String getTABLE_NAME();

    public abstract String getCOLUMN_ID();

    public abstract Connection getConnection();

    public int obterProximoID() throws SQLException {
        String query = "SELECT MAX(" + getCOLUMN_ID() + ") AS max_id FROM " + getTABLE_NAME();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int maxID = resultSet.getInt("max_id");
                return maxID + 1;
            }
        }
        return 1;
    }

    public void apagarID(int id) throws SQLException {
        String query = "DELETE FROM " + getTABLE_NAME() + " WHERE " + getCOLUMN_ID() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public  void apagarTabela() throws SQLException {
        String query = "DROP TABLE IF EXISTS " + getTABLE_NAME();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.execute();
        }
    }
}
