package com.yuren.util;


import java.util.HashMap;

public class BigObjectCache {

    static HashMap<String, Object> sObjectSoftMap;

    /**
     * key一定要保证唯一性。 建议使用 Object使用对象的 SimpleClassName + hasCode
     *
     * @param key
     * @param obj
     */
    public static synchronized void storeLargeObject(String key, Object obj) {
        if (sObjectSoftMap == null) {
            sObjectSoftMap = new HashMap<String, Object>();
        }
        sObjectSoftMap.put(key, obj);
    }

    /**
     * 在适当时机，例如onDestory()中调用clearLargeCache
     *
     * @param key
     * @return
     */
    public static Object popLargeObject(String key) {
        Object obj = null;
        if (null != sObjectSoftMap) {
            // setup 1:  get object
            obj = sObjectSoftMap.remove(key);
        }

        // setup 2: return
        return obj;
    }

    public static Object getLargeObject(String key) {
        Object obj = null;
        if (null != sObjectSoftMap) {
            // setup 1:  get object
            obj = sObjectSoftMap.get(key);
        }

        // setup 2: return
        return obj;
    }

    public static synchronized void clearLargeCache() {
        if (null != sObjectSoftMap) {
            sObjectSoftMap.clear();
            sObjectSoftMap = null;
        }
    }

    /*  使用SoftReference会造成数据不能正常读取，只能使用 强引用 */
    // static  HashMap<String, SoftReference<Object>> sObjectSoftMap =new HashMap<String, SoftReference<Object>>();
}