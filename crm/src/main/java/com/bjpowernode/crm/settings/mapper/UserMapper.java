package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.User;

import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据账号密码查询用户
     * @param map
     * @return
     */
    User selectUserByLoginActAndLoginPwd(Map<String, Object> map);


}