package com.yxuo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static final Config instance = new Config();
    private static final Map<String, String> propertiesMap = new HashMap<>();

    static {
        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            for (String key : properties.stringPropertyNames()) {
                propertiesMap.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(System.getProperty("user.dir"));
            System.exit(1);
        }
    }

    public static String getProperty(String key) {
        return propertiesMap.get(key);
    }

    public static Config getInstance() {
        return instance;
    }
}
