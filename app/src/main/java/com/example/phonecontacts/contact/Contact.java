// Contact类用于表示一个联系人对象，包含联系人的基本信息和首字母排序标识
package com.example.phonecontacts.contact;

import androidx.annotation.NonNull;

public class Contact {
    // 定义联系人的属性变量
    private String id;
    private String name;
    private String phone;
    private String sex;
    private String remark;
    private String firstLetter; // 首字母用于排序

    // 重写toString方法，用于返回Contact对象的字符串表示形式
    @NonNull
    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }

    // getId方法用于获取联系人的ID
    public String getId() {
        return id;
    }

    // setId方法用于设置联系人的ID
    public void setId(String id) {
        this.id = id;
    }

    // getName方法用于获取联系人的姓名
    public String getName() {
        return name;
    }

    // setName方法用于设置联系人的姓名
    public void setName(String name) {
        this.name = name;
    }

    // getPhone方法用于获取联系人的电话
    public String getPhone() {
        return phone;
    }

    // setPhone方法用于设置联系人的电话
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // getSex方法用于获取联系人的性别
    public String getSex() {
        return sex;
    }

    // setSex方法用于设置联系人的性别
    public void setSex(String sex) {
        this.sex = sex;
    }

    // getRemark方法用于获取联系人的备注
    public String getRemark() {
        return remark;
    }

    // setRemark方法用于设置联系人的备注
    public void setRemark(String remark) {
        this.remark = remark;
    }

    // getFirstLetter方法用于获取联系人姓名的首字母
    public String getFirstLetter() {
        return firstLetter;
    }

    // setFirstLetter方法用于设置联系人姓名的首字母
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    // Contact类的构造函数，只初始化首字母
    public Contact(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    // Contact类的构造函数，初始化所有属性
    public Contact(String id, String name, String phone, String sex, String remark, String firstLetter) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
        this.firstLetter = firstLetter;
    }

    // Contact类的构造函数，初始化除了首字母以外的所有属性
    public Contact(String id, String name, String phone, String sex, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
    }
}