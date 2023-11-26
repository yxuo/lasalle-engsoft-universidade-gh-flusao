package com.yxuo.util;

import java.util.List;

public class CLI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BRIGHT_BLACK = "\u001b[30;1m";
    public static final String ANSI_ITALIC = "\033[3m";
    public static final String TABLE_SPACE = "   ";

    public String value = "";

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String getSuccess(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String getError(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String getItalic(String text) {
        return ANSI_ITALIC + text + ANSI_RESET;
    }

    public static String getSpaces(Integer maxLength, Integer currentLength) {
        Integer length = maxLength - currentLength;
        return " ".repeat(length);
    }

    public static <T> List<Integer> getMaxLength(List<T> compare, List<Integer> maxLength) {
        for (int i = 0; i < compare.size(); i++) {
            if (i >= maxLength.size()) {
                maxLength.add(0);
            }
        }

        for (int i = 0; i < compare.size(); i++) {
            T item = compare.get(i);
            Integer length = item.toString().length();

            if (length > maxLength.get(i)) {
                maxLength.set(i, length);
            }
        }

        return maxLength;
    }

    public static <T> String getTableString(List<T> items, List<Integer> maxLength) {
        String response = "";
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            String itemStr = item.toString();
            response += itemStr + CLI.getSpaces(maxLength.get(i), itemStr.length()) + TABLE_SPACE;
        }
        response += "\n";
        return response;
    }

    @Override
    public String toString() {
        return value + ANSI_RESET;
    }
}