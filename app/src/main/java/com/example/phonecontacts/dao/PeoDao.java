package com.example.phonecontacts.dao;

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
    public static SQLiteDatabase db = DBUntil.db;

    public static List<PeoBean> getAllPeo() {
        List<PeoBean> list = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM d_peo", null);
        while(res.moveToNext()) {
            PeoBean peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), "");
            String name = res.getString(1);
            String firstCharacter = name.substring(0, 1);
            boolean re = firstCharacter.matches("^[a-zA-Z]*");
            if (re) {
                peoBean.setBeginZ(firstCharacter.toUpperCase());
            } else {
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

    public static PeoBean getOnePeo(String id) {
        Cursor res = db.rawQuery("SELECT * FROM d_peo WHERE s_id = ?", new String[]{id});
        PeoBean peoBean = null;
        while(res.moveToNext()) {
            peoBean = new PeoBean(res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4));
        }
        return peoBean;
    }

    public static List<PeoBean> getAllPeo(String id) {
        List<PeoBean> list = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM d_peo WHERE s_name LIKE '%" + id + "%' OR s_phone LIKE '%" + id + "%'", null);
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

    public static void createPeo(String name, String phone, String sex, String remark) {
        db.execSQL("INSERT INTO d_peo (s_name, s_phone, s_sex, s_remark) VALUES (?, ?, ?, ?)", new String[]{name, phone, sex, remark});
    }

    public static void deletePeo(String id) {
        db.execSQL("DELETE FROM d_peo WHERE s_id = ?", new String[]{id});
    }

    public static void updatePeo(String id, String name, String phone, String sex, String remark) {
        db.execSQL("UPDATE d_peo SET s_name = ?, s_phone = ?, s_sex = ?, s_remark = ? WHERE s_id = ?", new String[]{name, phone, sex, remark, id});
    }
}
