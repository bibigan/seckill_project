package com.java.mapper;


import com.java.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    @Select("select * from product ")
    List<Product> findAll();

    @Insert(" insert into product ( pname ) values (#{pname}) ")
    public int save(Product p);

    @Delete(" delete from product where id= #{id} ")
    public void delete(int id);

    @Select("select * from product where id= #{id} ")
    public Product get(int id);

    @Update("update product set pname=#{pname} where id=#{id} ")
    public int update(Product p);
}
