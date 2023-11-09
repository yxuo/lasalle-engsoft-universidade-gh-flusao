package com.yxuo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Query {
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static final <T> String select(T... a) {
        List<T> items = Arrays.asList(a);
        String query = "SELECT ";
        List<String> args = new ArrayList<String>();
        for (T item : items) {
            args.add(item.toString());
        }
        query += String.join(", ", args);
        return query + "\n";
    }

    public static final <T> String fn(String fname, T field) {
        return fname + " (" + field.toString() + ") AS " + fname + "_" + field.toString();
    }

    public static final <T> String fn(String fname, T field, String name) {
        return fname + " (" + field.toString() + ") AS " + name;
    }

    public static final <T> String fn1(String fname, T field, String prefix) {
        return fname + " (" + field.toString() + ") AS " + prefix + field.toString();
    }

    public static final <T> String fn2(String fname, T field, String prefix) {
        return fname + " (" + field.toString() + ") AS " + prefix + fname + "_" + field.toString();
    }

    public static final <T> String avg(T field, String prefix) {
        return "AVG (" + field.toString() + ") AS " + prefix + field.toString();
    }

    public static final <T> String join(String type, T table, T a, T b) {
        return type + " JOIN " + table.toString() + " ON " + a.toString() + " = " + b.toString() + "\n";
    }

    public static final <T> String as(T field, String prefix) {
        return field.toString() + " AS " + prefix + field.toString();
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static final <T> String groupBy(T... a) {
        String query = "GROUP BY ";
        List<T> items = Arrays.asList(a);
        List<String> args = new ArrayList<String>();
        for (T item : items) {
            args.add(item.toString());
        }
        query += String.join(", ", args);
        return query + "\n";
    }
}
