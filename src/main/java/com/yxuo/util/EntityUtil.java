package com.yxuo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.annotation.Column;
import com.yxuo.annotation.Entity;
import com.yxuo.model.BaseEntity;
import com.yxuo.util.namingStrategy.BaseNamingStrategy;

public final class EntityUtil {

    private static EntityUtil instance;
    private BaseNamingStrategy namingStrategy;

    EntityUtil() {
        String injectNamingStrategy = Config.getProperty("db.naming-strategy");
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

    // Annotation

    public static String getTableName(Class<?> clazz) {
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);

        if (entityAnnotation != null && !entityAnnotation.name().isEmpty()) {
            return entityAnnotation.name();
        } else {
            return Entity.Util.getDefaultName(clazz);
        }
    }

    public static String getColumnByName(Class<?> clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && fieldName == field.getName()) {
                return getColumnFieldName(field, column);
            }
        }
        return null;
    }

    public static List<String> getColumnNames(Class<?> clazz) {
        List<String> columnNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnNames.add(getColumnFieldName(field, column));
            }
        }

        return columnNames;
    }

    private static String getColumnFieldName(Field field, Column columnAnnotation) {
        if (!columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        } else {
            return Column.Util.getDefaultName(field.getName());
        }
    }

    public static String getIdColumn(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.id()) {
                return getColumnFieldName(field, column);
            }
        }
        return null;
    }
}
