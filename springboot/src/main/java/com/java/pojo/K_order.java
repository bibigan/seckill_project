package com.java.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class K_order {
    private int id;
    private String ocode;
    private Integer kid;
    private String uid;
    private Byte ostatus;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date create_time;

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Byte getOstatus() {
        return ostatus;
    }

    public void setOstatus(Byte ostatus) {
        this.ostatus = ostatus;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
