package com.java.controller;
import java.util.List;

import com.java.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.pojo.Orders;

@RestController
public class K_orderController {
    @Autowired
    OrdersService k_orderService;
    /*restful 部分*/
    @GetMapping("/K_orders")
    public PageInfo<Orders> list(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        PageHelper.startPage(start,size,"id desc");
        List<Orders> hs=k_orderService.list();
        System.out.println(hs.size());

        PageInfo<Orders> page = new PageInfo<>(hs,5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样

        return page;
    }
}
