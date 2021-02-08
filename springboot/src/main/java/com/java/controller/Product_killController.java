package com.java.controller;

import com.java.service.Item_killService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Product_killController {
    @Autowired
    Item_killService item_killService;
}
