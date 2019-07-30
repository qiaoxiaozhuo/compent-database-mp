package com.compent.database;


import java.util.ArrayList;
import java.util.List;

public class DbContextChoose {


    private static final ThreadLocal<String> contextHolder = new ThreadLocal();
    private static final List<String> dataSourceIds = new ArrayList();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return (String) contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

    public static List<String> getDataSourceIds() {
        return dataSourceIds;
    }
}
