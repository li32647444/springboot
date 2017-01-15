package com.libo.controller;

import com.libo.dao.SysUserDao;
import com.libo.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-12-28.
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserDao sysUserDao;

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public List<SysUser> userList(){
        return sysUserDao.findAll();
    }

    @PostMapping(value = "/users")
    public SysUser userAdd(@RequestParam("userName")String userName,@RequestParam("password")String password){
        SysUser add = new SysUser();
        add.setUserName(userName);
        add.setPassword(password);
        add.setCreateTime(new Date());
        add.setStatus("1");
        return sysUserDao.save(add);
    }

    @GetMapping("/users/{id}")
    public SysUser userFindOne(@PathVariable("id")Integer id){
        return sysUserDao.findOne(id);
    }

    //Put方法时：body选择x-www.form.urlencoded
    @PutMapping("/users/{id}")
    public SysUser userUpdate(@RequestParam("id")Integer id,
                              @RequestParam("password")String password){
        SysUser update = new SysUser();
        update.setId(id);
        update.setPassword(password);
        return sysUserDao.save(update);
    }
    @DeleteMapping("/users/{id}")
    public void userUpdate(@RequestParam("id")Integer id){
        sysUserDao.delete(id);
    }
}
