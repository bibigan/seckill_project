package com.java.mapper;


import com.java.pojo.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {
    @Select("select * from item ")
    List<Item> findAll();

    @Insert(" insert into item ( item_title,item_img,item_stock,item_price ) values (#{item_title},#{item_img},#{item_stock},#{item_price})")
    public int save(Item p);

    @Delete(" delete from item where id= #{id} ")
    public void delete(int id);

    @Select("select * from item where id= #{id} ")
    public Item get(int id);

    @Update("update item set item_title=#{item_title},item_img=#{item_title},item_stock=#{item_stock},item_price=#{item_price} where id=#{id} ")
    public int update(Item p);
}
