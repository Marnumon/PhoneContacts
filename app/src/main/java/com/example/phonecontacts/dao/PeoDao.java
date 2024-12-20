package com.example.phonecontacts.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.helper.DBHelper;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeoDao {
    public static DBHelper dbHelper;

    public static void setDbHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    // getAllPeo方法用于获取所有的联系人信息，并返回一个PeoBean对象列表
    public static List<PeoBean> getAllPeo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM d_peo", null);
        List<PeoBean> list = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                PeoBean peoBean = new PeoBean(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), "");
                String name = cursor.getString(1);
                String firstCharacter = name.substring(0, 1);
                // 如果首字符是英文字母，则直接转换为大写作为beginZ
                boolean re = firstCharacter.matches("^[a-zA-Z]*");
                if (re) {
                    peoBean.setBeginZ(firstCharacter.toUpperCase());
                } else {
                    // 如果首字符是汉字，则使用HanLP库转换为拼音，并取首字母大写作为beginZ
                    String regEx = "[\\u4e00-\\u9fa5]+";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher =pattern.matcher(firstCharacter);
                    if (matcher.find()) {
                        String pinyin = HanLP.convertToPinyinString(firstCharacter, " ", false);
                        String surname = pinyin.substring(0, 1);
                        peoBean.setBeginZ(surname.toUpperCase());
                    } else {
                        peoBean.setBeginZ("#");
                    }
                }
                list.add(peoBean);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return list;
    }

    // getAllPeo方法用于根据搜索关键字获取匹配的联系人信息列表
    public static List<PeoBean> getAllPeo(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM d_peo WHERE s_name LIKE '%" + id + "%' OR s_phone LIKE '%" + id + "%'", null);
        List<PeoBean> list = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                PeoBean peoBean = new PeoBean(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), "");
                String name = cursor.getString(1);
                String firstCharacter = name.substring(0, 1);
                boolean re = firstCharacter.matches("^[a-zA-Z]*");
                if (re) {
                    peoBean.setBeginZ(firstCharacter.toUpperCase());
                } else {
                    String regularExpression = "[\\u4e00-\\u9fa5]+";
                    Pattern pattern = Pattern.compile(regularExpression);
                    Matcher matcher =pattern.matcher(firstCharacter);
                    if (matcher.find()) {
                        String pinyin = HanLP.convertToPinyinString(firstCharacter, " ", false);
                        String surname = pinyin.substring(0, 1);
                        peoBean.setBeginZ(surname.toUpperCase());
                    } else {
                        peoBean.setBeginZ("#");
                    }
                }
                list.add(peoBean);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return list;
    }

    // getOnePeo方法用于根据ID获取单个联系人的信息，并返回一个PeoBean对象
    public static PeoBean getOnePeo(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM d_peo WHERE s_id = ?", new String[]{id});
        PeoBean peoBean = null;
        try {
            while (cursor.moveToNext()) {
                peoBean = new PeoBean(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            }
        } finally {
            cursor.close();
            db.close();
        }
        return peoBean;
    }

    // createPeo方法用于向数据库中插入一个新的联系人记录
    public static void createPeo(String name, String phone, String sex, String remark) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES (?, ?, ?, ?)", new String[]{name, phone, sex, remark});
        }
    }

    // deletePeo方法用于根据ID删除数据库中的一个联系人记录
    public static void deletePeo(String id) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("DELETE FROM d_peo WHERE s_id = ?", new String[]{id});
        }
    }

    // updatePeo方法用于根据ID更新数据库中的一个联系人记录
    public static void updatePeo(String id, String name, String phone, String sex, String remark) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE d_peo SET s_name = ?, s_phone = ?, s_sex = ?, s_remark = ? WHERE s_id = ?", new String[]{name, phone, sex, remark, id});
        }
    }
}