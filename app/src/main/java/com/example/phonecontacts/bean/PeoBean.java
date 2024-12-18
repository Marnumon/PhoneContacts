package com.example.phonecontacts.bean;

import androidx.annotation.NonNull;

public class PeoBean {
    // 定义私有成员变量，分别用于存储联系人的id、姓名、电话、性别、备注和beginZ字段
    private String id;
    private String name;
    private String phone;
    private String sex;
    private String remark;
    private String beginZ;

    // 重写toString方法，用于返回PeoBean对象的字符串表示形式
    // 使用@NonNull注解确保返回的字符串不为null
    @NonNull
    @Override
    public String toString() {
        return "PeoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", beginZ='" + beginZ + '\'' +
                '}';
    }

    // 获取id的getter方法
    public String getId() {
        return id;
    }

    // 设置id的setter方法
    public void setId(String id) {
        this.id = id;
    }

    // 获取姓名的getter方法
    public String getName() {
        return name;
    }

    // 设置姓名的setter方法
    public void setName(String name) {
        this.name = name;
    }

    // 获取电话的getter方法
    public String getPhone() {
        return phone;
    }

    // 设置电话的setter方法
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 获取性别的getter方法
    public String getSex() {
        return sex;
    }

    // 设置性别的setter方法
    public void setSex(String sex) {
        this.sex = sex;
    }

    // 获取备注的getter方法
    public String getRemark() {
        return remark;
    }

    // 设置备注的setter方法
    public void setRemark(String remark) {
        this.remark = remark;
    }

    // 获取beginZ的getter方法
    public String getBeginZ() {
        return beginZ;
    }

    // 设置beginZ的setter方法
    public void setBeginZ(String beginZ) {
        this.beginZ = beginZ;
    }

    // PeoBean类的构造函数，只初始化beginZ字段
    public PeoBean(String beginZ) {
        this.beginZ = beginZ;
    }

    // PeoBean类的构造函数，初始化所有字段
    public PeoBean(String id, String name, String phone, String sex, String remark, String beginZ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
        this.beginZ = beginZ;
    }

    // PeoBean类的构造函数，初始化除了beginZ以外的所有字段
    public PeoBean(String id, String name, String phone, String sex, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
    }
}