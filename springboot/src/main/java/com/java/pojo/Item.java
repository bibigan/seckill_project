package com.java.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private Integer id;
    private String item_title;
    private String item_img;
    private Float item_price;
    private Integer item_stock;
    private Integer version;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date create_time;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public Integer getItem_stock() {
        return item_stock;
    }

    public void setItem_stock(Integer item_stock) {
        this.item_stock = item_stock;
    }

    public Float getItem_price() {
        return item_price;
    }

    public void setItem_price(Float item_price) {
        this.item_price = item_price;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
