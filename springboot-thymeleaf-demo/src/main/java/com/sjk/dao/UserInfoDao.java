package com.sjk.dao;

import org.springframework.data.repository.CrudRepository;

import com.sjk.bean.SysUser;

public interface UserInfoDao extends CrudRepository<SysUser,Long> {
    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);
}