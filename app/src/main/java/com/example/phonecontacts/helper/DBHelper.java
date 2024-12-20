// DBHelper类继承自SQLiteOpenHelper，用于管理数据库的创建和版本管理
package com.example.phonecontacts.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 定义数据库名称和版本号
    private static final String DATABASE_NAME = "phone_contact";
    private static final int DATABASE_VERSION = 1;

    // DBHelper构造函数，接收上下文对象，并传递到父类
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate方法在数据库第一次创建时被调用，用于创建表和初始化数据
    @Override
    public void onCreate(SQLiteDatabase db) {
        createContactTable(db);
        insertInitialData(db);
    }

    // createContactTable方法用于创建联系人表
    private void createContactTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contact (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // 定义自增主键id
                "name VARCHAR(20), " + // 定义姓名字段
                "phone VARCHAR(20), " + // 定义电话字段
                "sex VARCHAR(20), " + // 定义性别字段
                "remark VARCHAR(20)" + // 定义备注字段
                ")");
    }

    // insertInitialData方法用于插入初始数据到联系人表
    private void insertInitialData(SQLiteDatabase db) {
        // 插入一系列联系人数据
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('丰川祥子', '12345678901', '女', '客服小祥')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('千早爱音', '23456789012', '女', '唐笑宗')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('椎名立希', '34567890123', '女', '灯卫兵')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('长崎素世', '45678901234', '女', '什么都会做的')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('要乐奈', '56789012345', '女', '为什么要演奏春日影')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('若叶睦', '67890123456', '女', '睦头人')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('4dX#8kP', '78901234567', '男', '2cF%5tH')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('fZ5&fWp', '89012345678', '男', '3wS+qL')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('+8mN*kP', '90123456789', '男', 'bY6&zG')");
        db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES ('c5G@3nB', '01234567890', '男', '1yR^9lK')");
    }

    // onUpgrade方法在数据库需要升级时被调用，用于处理数据库表的重建
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contact"); // 删除旧的联系人表
        onCreate(db); // 重新调用onCreate创建新表和初始化数据
    }
}