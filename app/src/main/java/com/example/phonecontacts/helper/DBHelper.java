package com.example.phonecontacts.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 定义数据库的名称
    private static final String DB_NAME = "db.addBook.db";
    // 定义数据库的版本号
    private static final int VERSION = 5;
    // 定义一个静态的SQLiteDatabase对象，用于操作数据库
//    public static SQLiteDatabase db;

    // DBUntil类的构造函数，接收一个Context对象作为参数，并调用父类的构造函数初始化数据库
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // onCreate方法在数据库第一次创建时被调用，用于创建数据库表和初始化数据
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 删除已存在的d_peo表，如果存在的话
        db.execSQL("DROP TABLE IF EXISTS d_peo");
        // 创建一个新的d_peo表，包含id、姓名、电话、性别和备注五个字段
        db.execSQL("CREATE TABLE d_peo (" +
                "s_id INTEGER PRIMARY KEY AUTOINCREMENT, " + // id字段，自动增长
                "s_name VARCHAR(20), " + // 姓名字段，最多20个字符
                "s_phone VARCHAR(20), " + // 电话字段，最多20个字符
                "s_sex VARCHAR(20), " + // 性别字段，最多20个字符
                "s_remark VARCHAR(20)" + // 备注字段，最多20个字符
                ")");
        // 向d_peo表中插入一些初始数据
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('丰川祥子', '46541321231', '女', '客服小祥')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('千早爱音', '84653111234', '女', '唐笑宗')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('欠草爱音', '86461315315', '男', '唐笑宗')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('椎名立希', '35438873455', '女', '灯卫兵')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('长崎素世', '12378686652', '女', '长期素食导致的')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('1.13.2', '54131213453', '男', '海洋更新')");
    }

    // onUpgrade方法在数据库需要升级时被调用，用于处理数据库的版本升级
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 当数据库需要升级时，调用onCreate方法重新创建数据库和表，这会删除旧数据
        onCreate(sqLiteDatabase);
    }
}
