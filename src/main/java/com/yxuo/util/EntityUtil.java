package com.yxuo.util;

import java.util.List;

import com.yxuo.model.BaseEntity;

public class EntityUtil {
    public static <T extends BaseEntity> int getId(T model) {
        return model.getId();
    }

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
}
