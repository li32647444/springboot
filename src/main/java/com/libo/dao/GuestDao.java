package com.libo.dao;

import com.libo.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016-12-28.
 */
public interface GuestDao extends JpaRepository<Guest,Integer>{

    public List<Guest> findByName(String name);
}
