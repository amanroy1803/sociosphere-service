package com.aman.socio_sphere.utils;

import java.lang.reflect.Field;

public class UserUtils {
    public static void copyNotNullFields(Object source, Object target) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (null != value) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(e.getMessage());
            }
        }
    }
}
