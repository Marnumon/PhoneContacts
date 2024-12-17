package com.example.phonecontacts.until;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUntil extends SQLiteOpenHelper {
    private static final String DB_NAME = "db.addBook.db";
    private static final int VERSION = 5;
    public static SQLiteDatabase db = null;

    public DBUntil(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS d_peo");
        db.execSQL("CREATE TABLE d_peo (" +
                "s_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "s_name VARCHAR(20), " +
                "s_phone VARCHAR(20), " +
                "s_sex VARCHAR(20), " +
                "s_remark VARCHAR(20))");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('丰川祥子', '46541321231', '女', '客服小祥')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('千早爱音', '84653111234', '女', '唐笑宗')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('欠草爱音', '86461315315', '男', '唐笑宗')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('椎名立希', '35438873455', '女', '灯卫兵')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('长崎素世', '12378686652', '女', '长期素食导致的')");
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES ('1.13.2', '54131213453', '男', '海洋更新')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }
}
