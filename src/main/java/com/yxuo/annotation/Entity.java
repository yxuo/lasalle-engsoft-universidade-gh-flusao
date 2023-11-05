package com.yxuo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
    String name() default "";

    public static class Util {
        public static String getDefaultName(Class<?> clazz) {
            return clazz.getSimpleName();
        }
    }
}
