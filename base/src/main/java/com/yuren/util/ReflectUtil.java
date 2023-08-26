package com.yuren.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;


public class ReflectUtil {

    public static HashMap<String, Object> getFiledValue(Object target, String... fnameArray) {
        if (target == null
                || fnameArray == null
                || fnameArray.length <= 0
                ) {
            return null;
        }

        HashMap<String, Object> valueMap = new HashMap<String, Object>(fnameArray.length);

        Class clazz = target.getClass();
        for (String fname : fnameArray) {
            try {
                Field field = clazz.getDeclaredField(fname);   //获取定义的类属性
                if (!Modifier.isPublic(field.getModifiers())) {    //设置非共有类属性权限
                    field.setAccessible(true);
                }
                valueMap.put(fname, field.get(target));//返回类属性值
            } catch (Exception e) {
                return null;
            }
        }

        return valueMap;
    }

    public static HashMap<String, Object> getStaticFiledValue(Class clazz, String... fnameArray) {
        if (clazz == null
                || fnameArray == null
                || fnameArray.length <= 0
                ) {
            return null;
        }

        HashMap<String, Object> valueMap = new HashMap<String, Object>(fnameArray.length);


        for (String fname : fnameArray) {
            try {
                Field field = clazz.getDeclaredField(fname);   //获取定义的类属性
                if (!Modifier.isPublic(field.getModifiers())) {    //设置非共有类属性权限
                    field.setAccessible(true);
                }


                valueMap.put(fname, field.get(null));//返回类属性值
            } catch (Exception e) {
                return null;
            }
        }

        return valueMap;
    }


    public static Object getFiledValue(Object target, String fname) {
        if (target == null
                || fname == null
                || fname.length() <= 0
                ) {
            return null;
        }


        Class clazz = target.getClass();
        try {
            Field field = clazz.getDeclaredField(fname);   //获取定义的类属性
            if (!Modifier.isPublic(field.getModifiers())) {    //设置非共有类属性权限
                field.setAccessible(true);
            }
            return field.get(target);//返回类属性值
        } catch (Exception e) {
            return null;
        }
    }


    public static Object getStaticFiledValue(Class clazz, String fname) {
        if (clazz == null
                || fname == null
                || fname.length() <= 0
                ) {
            return null;
        }


        try {
            Field field = clazz.getDeclaredField(fname);   //获取定义的类属性
            if (!Modifier.isPublic(field.getModifiers())) {    //设置非共有类属性权限
                field.setAccessible(true);
            }
            return field.get(null);//返回类属性值
        } catch (Exception e) {
            return null;
        }
    }
}
