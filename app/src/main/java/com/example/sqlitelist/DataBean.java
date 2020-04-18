package com.example.sqlitelist;

public class DataBean {
    public String id;

    public String name;

    public String tel;

    public String group;

    public boolean isCheck;

    public DataBean(String id, String name, String tel, String group) {
        this.name = name;
        this.tel = tel;
        this.group = group;
        this.id=id;
    }

}
