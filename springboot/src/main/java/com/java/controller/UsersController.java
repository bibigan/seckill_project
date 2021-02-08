package com.java.controller;
import java.util.ArrayList;
import java.util.List;

import com.java.pojo.Item;
import com.java.pojo.ItemInVuex;
import com.java.pojo.Item_kill;
import com.java.pojo.Users;
import com.java.service.ItemService;
import com.java.service.Item_killService;
import com.java.service.UsersService;
import com.java.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    ItemService itemService;
    @Autowired
    Item_killService item_killService;

    @GetMapping("/users")
    public String listUsers() throws Exception {
        String jsonString=JSON.toJSONString(usersService.list());
        return jsonString;
    }
    /*
    1. 通过参数Users获取浏览器提交的账号密码
    2. 通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
    3. 判断用户名是否存在
    3.1 如果已经存在，就返回Result.fail,并带上错误信息
    3.2 如果不存在，则加入到数据库中，并返回 Result.success()*/
    @PostMapping("/register")
    public String add(@RequestBody Users user) throws Exception {
        System.out.println("访问/register");
//        System.out.println("参数："+user.getUser_name()+","+user.getUser_password()+","+user.getUser_email());
        String name =  user.getUser_name();
//        String password = user.getUser_password();
        name = HtmlUtils.htmlEscape(name);
//        user.setUser_name(name);

        boolean exist = usersService.isExist(name);

        if(exist){
//            console("存在用户名"+name);
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);

        }
//        System.out.println("不存在用户名"+name);
//        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
//        int times = 2;
//        String algorithmName = "md5";
//
//        String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
//
//        user.setSalt(salt);
//        Users user=new Users(name,password,email);
//        user.setPassword(encodedPassword);
//        user.setUser_password(password);
        usersService.add(user);

        return Result.success();
    }
    /*
    1. 账号密码注入到 userParam 对象上
    2. 把账号通过HtmlUtils.htmlEscape进行转义
    3. 根据账号和密码获取User对象
    3.1 如果对象为空，则返回错误信息
    3.2 如果对象存在，则把用户对象放在 session里，并且返回成功信息
    * */
    @PostMapping("/login")
    public String login(@RequestBody Users users, HttpSession session){
        System.out.println("访问/login");

        String user_name=users.getUser_name();
        user_name = HtmlUtils.htmlEscape(user_name);
        String user_password=users.getUser_password();
        System.out.println("username:"+users.getUser_name());
        Users res=usersService.getByName(user_name);
        System.out.println("username:"+users.getUser_name());
        if(null==res||!users.getUser_password().equals(res.getUser_password())){
            String message ="账号密码错误";
            return Result.fail(message);
        }
        else{
            System.out.println("user："+res);
            session.setAttribute("user", res);
            return Result.success();
        }
    }
    @GetMapping("/items")
    public Object listItems() throws Exception {
        /*先得到ik的list，遍历list，为每个ik找到对应的i，加入到新list*/
        System.out.println("访问/items");
        List<Item_kill> item_kills=item_killService.list();
        List<ItemInVuex> items=new ArrayList<>();
        for (Item_kill item_kill:item_kills){
            int item_id=item_kill.getItem_id();
            Item item=itemService.get(item_id);
            ItemInVuex itemInVuex=new ItemInVuex(item.getItem_title(),item.getItem_img(),item.getItem_price(),item.getItem_stock(),item_kill.getItem_kill_seckillStartTime(),item_kill.getItem_kill_seckillEndTime());
            items.add(itemInVuex);
        }
//        String jsonString=JSON.toJSONString(items);
//        return jsonString;
        return items;
    }
    @GetMapping("/orders")
    public Object listOrders(HttpSession session) throws Exception {
        Users users=(Users) session.getAttribute("user");
        return null;
    }
}
