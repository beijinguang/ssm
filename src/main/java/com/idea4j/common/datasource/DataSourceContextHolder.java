package com.idea4j.common.datasource;

/**
 * Created by markee on 2016/9/6.
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDb(String db) {
        contextHolder.set(db);
    }

    public static String getDb() {
        return ((String) contextHolder.get());
    }

    public static void clearDb() {
        contextHolder.remove();
    }
}
