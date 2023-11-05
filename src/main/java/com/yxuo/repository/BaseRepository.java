package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yxuo.model.BaseEntity;
import com.yxuo.util.DBConnector;

public abstract class BaseRepository {

    BaseRepository() {
        // this.e
    }

    private int maxId = -1;

    // public abstract String getTableName();

    // public abstract String getColumnId();

    /**
     * Example:
     * <pre>
     * class UserRepository {
     *     private UserEntity entity;
     *     public <T extends BaseEntity> T getEntity() {
     *         @SuppressWarnings(&#40;"unchecked")
     *         T entity = (T) this.entity;
     *         return entity;
     *     }
     * }
     * </pre>
     */
    public abstract <T extends BaseEntity> T getEntity();

    public abstract Connection getConnection();

    public void setMaxIdFromDB() throws SQLException {
        String query = "SELECT MAX(" + getEntity().getIdColumn() + ") AS max_id FROM " + getEntity().getTableName();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int maxID = resultSet.getInt("max_id");
                this.maxId = maxID;
            }
        }
    }

    public int obterProximoID() throws SQLException {
        if (maxId == -1) {
            setMaxIdFromDB();
        }
        this.maxId += 1;
        return this.maxId;
    }

    public void apagarID(int id) throws SQLException {
        String query = "DELETE FROM " + getEntity().getTableName() + " WHERE " + getEntity().getIdColumn() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void apagarTabela() throws SQLException {
        String query = "DROP TABLE IF EXISTS " + getEntity().getTableName();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.execute();
        }
    }
}
