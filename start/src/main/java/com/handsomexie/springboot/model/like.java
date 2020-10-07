package com.handsomexie.springboot.model;

public class like {
    public like(String a,String b,String c){
        upid=a;
        username=b;
        picname=c;
    }
    private String upid;

    private String username;

    private String picname;

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }
}