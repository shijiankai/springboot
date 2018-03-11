
package com.sjk.service;

import com.sjk.bean.SysUser;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);
}