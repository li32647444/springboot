package com.libo.controller;


import com.libo.dao.GuestDao;
import com.libo.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * Created by lmk on 2017/1/16.
 */
@Controller
@RequestMapping("/view")
public class PageController {
    @Autowired
    private GuestDao guestDao;

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/we")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

    @RequestMapping("/hello")
    public ModelAndView hello(){
        ModelAndView mav = new ModelAndView("ad");
        //  mav.addObject("content", "hello");
        return mav;
    }
    @RequestMapping("/hello2")
    public String helloJsp(Map<String,Object> map){
//        System.out.println("HelloController.helloJsp().hello="+"hello");
        map.put("hello", "hello");
        return "ad";
    }

    @RequestMapping("/guests/{id}")
    public String guestFindOne(@PathVariable("id")Integer id,Map<String,Object> map){
       // return guestDao.findOne(id);
        map.put("guest",guestDao.findOne(id).getName());
        return "ad";
    }
}
