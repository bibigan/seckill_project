package com.java.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OrdersInVuex {
    private Integer id;
    private String title;
    private String img;
    private Float price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date create_time;
    private String ocode;
    private Integer number;
    private Float total;

    public OrdersInVuex(){

    }
    public OrdersInVuex(Integer id,String title, String img, Float price, Date create_time, String ocode, Integer number) {
        this.id=id;
        this.title = title;
        this.img = img;
        this.price = price;
        this.create_time = create_time;
        this.ocode = ocode;
        this.number = number;
        this.total = 0.00f;
    }

    @Override
    public String toString() {
        return "OrdersInVuex{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", create_time=" + create_time +
                ", ocode='" + ocode + '\'' +
                ", number=" + number +
                ", total=" + total +
                '}';
    }
}
