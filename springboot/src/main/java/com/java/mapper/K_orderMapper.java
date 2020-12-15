package com.java.mapper;


import com.java.pojo.K_order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface K_orderMapper {
    @Select("select * from k_order ")
    List<K_order> findAll();

    @Insert(" insert into k_order ( ocode ) values (#{ocode}) ")
    public int save(K_order p);

    @Delete(" delete from k_order where id= #{id} ")
    public void delete(int id);

    @Select("select * from k_order where id= #{id} ")
    public K_order get(int id);

    @Update("update k_order set kid=#{kid} where id= #{id} ")
    public int update(K_order p);
}
