package com.java.mapper;

import com.java.pojo.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersMapper {
    @Select("select * from users ")
    List<Users> findAll();

    @Insert(" insert into users ( uname ) values (#{uname}) ")
    public int save(Users users);

    @Delete(" delete from users where id= #{id} ")
    public void delete(int id);

    @Select("select * from users where id= #{id} ")
    public Users get(int id);

    @Update("update users set name=#{uname} where id=#{id} ")
    public int update(Users users);
}
