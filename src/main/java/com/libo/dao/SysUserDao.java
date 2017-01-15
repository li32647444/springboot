package com.libo.dao;

import com.libo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016-12-28.
 */
public interface SysUserDao extends JpaRepository<SysUser,Integer>{

    /*public List<SysUser> findByUserNameAndPassword(String userName,String password);
    public List<SysUser> findByStatesLess(String states);*/

}
