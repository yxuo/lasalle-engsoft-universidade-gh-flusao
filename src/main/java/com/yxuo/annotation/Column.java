package com.yxuo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name() default "";
    
    boolean id() default false;
    
    public static class Util {
        public static String getDefaultName(String fieldName) {
            if (fieldName != null && !fieldName.isEmpty()) {
                return fieldName;
            } else {
                throw new IllegalArgumentException("O nome do campo não pode ser vazio ou nulo.");
            }
        }
    }
}
