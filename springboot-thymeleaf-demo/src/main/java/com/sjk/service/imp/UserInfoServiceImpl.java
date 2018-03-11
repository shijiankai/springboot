package com.sjk.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjk.bean.SysUser;
import com.sjk.dao.UserInfoDao;
import com.sjk.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public SysUser findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}