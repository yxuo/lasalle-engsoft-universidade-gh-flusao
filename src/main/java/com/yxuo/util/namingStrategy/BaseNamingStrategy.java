package com.yxuo.util.namingStrategy;

public class BaseNamingStrategy implements IBaseNamingStrategy {
    private static final BaseNamingStrategy instance = new BaseNamingStrategy();

    public static BaseNamingStrategy getInstance() {
        return instance;
    }
    
    @Override
    public String handleNameStrategy(String name) {
        return name;
    }
}
