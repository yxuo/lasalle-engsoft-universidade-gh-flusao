package com.yxuo.util.namingStrategy;

public class UppercaseNamingStrategy extends BaseNamingStrategy {
    @Override
    public String handleNameStrategy(String name) {
        return convertCamelToSnake(name);
    }

    private static String convertCamelToSnake(String camelCase) {
        if (camelCase == null) {
            return "";
        }

        // Remove custom suffix
        camelCase = camelCase.replace("AC", "");

        if (camelCase.isEmpty()) {
            return "";
        }

        StringBuilder snakeCase = new StringBuilder();
        snakeCase.append(Character.toUpperCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            char previousChar = camelCase.charAt(i - 1);

            if (Character.isUpperCase(currentChar) && Character.isLowerCase(previousChar)) {
                snakeCase.append('_');
                snakeCase.append(Character.toUpperCase(currentChar));
            } else {
                snakeCase.append(Character.toUpperCase(currentChar));
            }
        }

        return snakeCase.toString();

    }
}
