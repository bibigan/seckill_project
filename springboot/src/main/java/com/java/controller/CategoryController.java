package com.java.controller;
import java.util.List;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.pojo.Category;
import com.java.service.CategoryService;

@RestController
public class CategoryController {
    @Autowired CategoryService CategoryService;

    /*restful 部分*/
    @GetMapping("/Categoryes")
    public PageInfo<Category> list(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        PageHelper.startPage(start,size,"id desc");
        List<Category> hs=CategoryService.list();
        System.out.println(hs.size());

        PageInfo<Category> page = new PageInfo<>(hs,5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样

        return page;
    }

    @GetMapping("/Categoryes/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category h=CategoryService.get(id);
        System.out.println(h);
        return h;
    }

    @PostMapping("/Categoryes")
    public String add(@RequestBody Category h) throws Exception {
        CategoryService.add(h);
        return "success";
    }
    @DeleteMapping("/Categoryes/{id}")
    public String delete(Category h) throws Exception {
        CategoryService.delete(h.getId());
        return "success";
    }
    @PutMapping("/Categoryes/{id}")
    public String update(@RequestBody Category h) throws Exception {
        CategoryService.update(h);
        return "success";
    }

    /*页面跳转 部分*/
    @RequestMapping(value="/listCategory", method=RequestMethod.GET)
    public ModelAndView listCategory(){
        ModelAndView mv = new ModelAndView("listCategory");
        return mv;
    }

    @RequestMapping(value="/editCategory", method=RequestMethod.GET)
    public ModelAndView editCategory(){
        ModelAndView mv = new ModelAndView("editCategory");
        System.out.println("/editCategory");
        return mv;
    }
}