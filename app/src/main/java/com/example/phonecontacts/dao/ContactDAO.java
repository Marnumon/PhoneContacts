// ContactDAO类提供了对联系人数据库操作的一系列方法
package com.example.phonecontacts.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonecontacts.contact.Contact;
import com.example.phonecontacts.helper.DBHelper;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactDAO {
    // 静态变量dbHelper用于持有数据库帮助类的实例
    public static DBHelper dbHelper;

    // setDbHelper方法用于初始化数据库帮助类的实例
    public static void setDbHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    // getAllContacts方法用于获取所有的联系人信息，并返回一个Contact对象列表
    public static List<Contact> getAllContacts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contact", null);
        List<Contact> list = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), "");
                // 根据联系人姓名的首字符设置排序字母
                setFirstLetter(contact, cursor.getString(1));
                list.add(contact);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return list;
    }

    // getAllContacts方法用于根据搜索关键字获取匹配的联系人信息列表
    public static List<Contact> getAllContacts(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contact WHERE name LIKE '%" + id + "%' OR phone LIKE '%" + id + "%'", null);
        List<Contact> list = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), "");
                // 根据联系人姓名的首字符设置排序字母
                setFirstLetter(contact, cursor.getString(1));
                list.add(contact);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return list;
    }

    // getOneContact方法用于根据ID获取单个联系人的信息，并返回一个Contact对象
    public static Contact getOneContact(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contact WHERE id = ?", new String[]{id});
        Contact contact = null;
        try {
            if (cursor.moveToNext()) {
                contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            }
        } finally {
            cursor.close();
            db.close();
        }
        return contact;
    }

    // createContact方法用于向数据库中插入一个新的联系人记录
    public static void createContact(String name, String phone, String sex, String remark) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("INSERT INTO contact (name, phone, sex, remark) VALUES (?, ?, ?, ?)", new String[]{name, phone, sex, remark});
        }
    }

    // deleteContact方法用于根据ID删除数据库中的一个联系人记录
    public static void deleteContact(String id) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("DELETE FROM contact WHERE id = ?", new String[]{id});
        }
    }

    // updateContact方法用于根据ID更新数据库中的一个联系人记录
    public static void updateContact(String id, String name, String phone, String sex, String remark) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE contact SET name = ?, phone = ?, sex = ?, remark = ? WHERE id = ?", new String[]{name, phone, sex, remark, id});
        }
    }

    // 私有方法，用于设置联系人姓名的首字符
    private static void setFirstLetter(Contact contact, String name) {
        String firstCharacter = name.substring(0, 1);
        boolean isLetter = firstCharacter.matches("^[a-zA-Z]*");
        if (isLetter) {
            contact.setFirstLetter(firstCharacter.toUpperCase());
        } else {
            String regularExpression = "[\\u4e00-\\u9fa5]+";
            Pattern pattern = Pattern.compile(regularExpression);
            Matcher matcher = pattern.matcher(firstCharacter);
            if (matcher.find()) {
                String pinyin = HanLP.convertToPinyinString(firstCharacter, " ", false);
                String surname = pinyin.substring(0, 1);
                contact.setFirstLetter(surname.toUpperCase());
            } else {
                contact.setFirstLetter("#");
            }
        }
    }
}