package com.idea4j.common.datasource;

/**
 *
 * @author markee
 * @date 2016/9/6
 */
public class DataSourceContextHolder {
    private DataSourceContextHolder() {
    }

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDb(String db) {
        CONTEXT_HOLDER.set(db);
    }

    public static String getDb() {
        return ((String) CONTEXT_HOLDER.get());
    }

    public static void clearDb() {
        CONTEXT_HOLDER.remove();
    }
}
