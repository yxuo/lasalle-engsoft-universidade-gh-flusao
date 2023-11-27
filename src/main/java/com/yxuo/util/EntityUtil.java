package com.yxuo.util;

import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.util.namingStrategy.BaseNamingStrategy;

public final class EntityUtil {

    private static EntityUtil instance;
    private BaseNamingStrategy namingStrategy;

    EntityUtil() {
        String injectNamingStrategy = Config.get("db.naming-strategy");
        if (injectNamingStrategy == null) {
            this.namingStrategy = BaseNamingStrategy.getInstance();
        } else {
            setNamingStrategy(injectNamingStrategy);
        }
    }

    // Naming strategy

    public static EntityUtil getInstance() {
        if (instance == null) {
            instance = new EntityUtil();
        }
        return instance;
    }

    private void setNamingStrategy(String className) {
        try {
            Class<?> strategyClass = Class.forName(className);
            Object instance = strategyClass.getDeclaredConstructor().newInstance();
            if (instance instanceof BaseNamingStrategy) {
                namingStrategy = (BaseNamingStrategy) instance;
            } else {
                throw new IllegalArgumentException("Provided class is not a BaseNamingStrategy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String handleNamingStrategy(String name) {
        return namingStrategy.handleNameStrategy(name);
    }

    // List managment

    public static <T extends BaseEntity> T getFromList(int id, List<T> models) {
        for (T model : models) {
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    public static <T extends BaseEntity> int getIdFromList(int id, List<T> models) {
        for (T model : models) {
            if (model.getId() == id) {
                return model.getId();
            }
        }
        return -1;
    }

    public static <T extends BaseEntity> int getId(T model) {
        return model.getId();
    }
}
