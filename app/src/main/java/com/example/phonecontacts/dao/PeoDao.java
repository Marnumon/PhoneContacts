package com.example.phonecontacts.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonecontacts.bean.PeoBean;
import com.example.phonecontacts.until.DBUntil;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeoDao {
    // 使用DBUntil类的db成员变量，它是一个SQLiteDatabase对象，用于执行数据库操作
    public static SQLiteDatabase db = DBUntil.db;

    // getAllPeo方法用于获取所有的联系人信息，并返回一个PeoBean对象列表
    public static List<PeoBean> getAllPeo() {
        List<PeoBean> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM d_peo", null);
        while(res.moveToNext()) {
            PeoBean peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), "");
            String name = res.getString(1);
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
        return list;
    }

    // getOnePeo方法用于根据ID获取单个联系人的信息，并返回一个PeoBean对象
    public static PeoBean getOnePeo(String id) {
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM d_peo WHERE s_id = ?", new String[]{id});
        PeoBean peoBean = null;
        while(res.moveToNext()) {
            peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4));
        }
        return peoBean;
    }

    // getAllPeo方法用于根据搜索关键字获取匹配的联系人信息列表
    public static List<PeoBean> getAllPeo(String id) {
        List<PeoBean> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM d_peo WHERE s_name LIKE '%" + id + "%' OR s_phone LIKE '%" + id + "%'", null);
        while(res.moveToNext()) {
            PeoBean peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), "");
            String name = res.getString(1);
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
        return list;
    }

    // createPeo方法用于向数据库中插入一个新的联系人记录
    public static void createPeo(String name, String phone, String sex, String remark) {
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES (?, ?, ?, ?)", new String[]{name, phone, sex, remark});
    }

    // deletePeo方法用于根据ID删除数据库中的一个联系人记录
    public static void deletePeo(String id) {
        db.execSQL("DELETE FROM d_peo WHERE s_id = ?", new String[]{id});
    }

    // updatePeo方法用于根据ID更新数据库中的一个联系人记录
    public static void updatePeo(String id, String name, String phone, String sex, String remark) {
        db.execSQL("UPDATE d_peo SET s_name = ?, s_phone = ?, s_sex = ?, s_remark = ? WHERE s_id = ?", new String[]{name, phone, sex, remark, id});
    }
}