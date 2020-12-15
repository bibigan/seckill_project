package com.java.mapper;


import com.java.pojo.Product_kill;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_killMapper {
    @Select("select * from product_kill ")
    List<Product_kill> findAll();

    @Insert(" insert into product_kill ( pid,start_time,end_time, create_time) values (#{pid},#{start_time},#{end_time}, #{create_time}) ")
    public int save(Product_kill pk);

    @Delete(" delete from product_kill where id= #{id} ")
    public void delete(int id);

    @Select("select * from product_kill where id= #{id} ")
    public Product_kill get(int id);

    @Update("update product_kill set pid=#{pid}  where id=#{id} ")
    public int update(Product_kill pk);
}
