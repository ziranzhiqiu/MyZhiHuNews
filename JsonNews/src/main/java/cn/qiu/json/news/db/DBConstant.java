package cn.qiu.json.news.db;


public class DBConstant {
    public static final String DB_NAME = "JsonNews";
    public static final int DB_VERSION = 1;

    public static final String TABLE_READ = "read";
    public static final String COLUMN_ID = "newid";

    public static final String CREATE_TABLE_READ = "create table " + TABLE_READ + "(" + COLUMN_ID + " text)";
}
