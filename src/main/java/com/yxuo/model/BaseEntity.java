package com.yxuo.model;
import com.yxuo.util.EntityUtil;

public abstract class BaseEntity {
    private EntityUtil entityUtil;

    BaseEntity() {
        this.entityUtil = EntityUtil.getInstance();
    }

    public abstract Integer getId();
    public abstract String getIdColumn();
    public abstract String getTableName();

    protected final String handleNamingStrategy(String name) {
        return this.entityUtil.handleNamingStrategy(name);
    }

}
