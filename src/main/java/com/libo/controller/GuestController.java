package com.libo.controller;

import com.libo.dao.GuestDao;
import com.libo.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016-12-28.
 */
@RestController
public class GuestController {

    @Autowired
    private GuestDao guestDao;

    @RequestMapping(value = "/guests",method = RequestMethod.GET)
    public List<Guest> userList(){
        return guestDao.findAll();
    }

    @PostMapping(value = "/guests")
    public Guest guestAdd(@RequestParam("name")String name)throws  Exception{
        List<Guest> guests = guestDao.findByName(name);
        if(guests!=null && guests.size()>0){
            throw  new Exception("该来宾姓名已存在！");
        } else {
            Guest add = new Guest();
            add.setName(name);
            add.setStatus("0");
            return guestDao.save(add);
        }
    }

    @GetMapping("/guestsByName/{name}")
    public Guest guestsByName(@PathVariable("name")String name){
        List<Guest> guests = guestDao.findByName(name);
        if(guests!=null && guests.size()>0){
            return guests.get(0);
        } else {
            return null;
        }
    }

    @GetMapping("/guests/{id}")
    public Guest guestFindOne(@PathVariable("id")Integer id){
        return guestDao.findOne(id);
    }

    //Put方法时：body选择x-www.form.urlencoded
    @PostMapping("/guestUpdate")
    public Guest guestUpdate(@RequestParam("id")Integer id,
                              @RequestParam("name")String name) throws Exception{
        if(null==id || "".equals(id)){
            throw  new Exception("传入数据缺少Id...！");
        }
        List<Guest> guests = guestDao.findByName(name);
        if(guests!=null && guests.size()>0 && id != guests.get(0).getId()){//重复姓名
            throw  new Exception("该来宾姓名已存在！");
        } else {
            Guest update = new Guest();
            update.setId(id);
            update.setName(name);
            return guestDao.save(update);
        }
    }

    @PostMapping("/guestDelete")
    public boolean guestDelete(@RequestParam("id")Integer id){
        boolean flag = false;
        try{
            guestDao.delete(id);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
