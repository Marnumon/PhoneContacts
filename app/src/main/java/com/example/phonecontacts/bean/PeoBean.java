package com.example.phonecontacts.bean;

import androidx.annotation.NonNull;

public class PeoBean {
    private String id;
    private String name;
    private String phone;
    private String sex;
    private String remark;
    private String beginZ;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeginZ() {
        return beginZ;
    }

    public void setBeginZ(String beginZ) {
        this.beginZ = beginZ;
    }

    public PeoBean(String beginZ) {
        this.beginZ = beginZ;
    }

    public PeoBean(String id, String name, String phone, String sex, String remark, String beginZ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
        this.beginZ = beginZ;
    }

    public PeoBean(String id, String name, String phone, String sex, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.remark = remark;
    }
}
