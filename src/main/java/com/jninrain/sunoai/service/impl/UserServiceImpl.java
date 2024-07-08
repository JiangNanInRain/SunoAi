package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.UserMapper;
import com.jninrain.sunoai.entity.User;
import com.jninrain.sunoai.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/14:54
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public String getPwdByUserName(String userName){
        return userMapper.getPwdByUserName(userName);

    }

    @Override
    public String getIdByUserName(String userName) {
        return userMapper.getIdByUserName(userName);
    }

    @Override
    public String getDisplayNameById(String id){
        return userMapper.getDisplayNameById(id);
    }

    @Override
    public boolean save(User user) {
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public User getUserInfoByUserId(Long id) {
        return userMapper.getUserInfoByUserId(id);
    }


}
