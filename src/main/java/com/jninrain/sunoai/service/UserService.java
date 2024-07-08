package com.jninrain.sunoai.service;

import cn.hutool.core.date.chinese.SolarTerms;
import com.jninrain.sunoai.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/14:53
 * @Description:
 */
public interface UserService {

    String getPwdByUserName(String userName);

    String getIdByUserName(String userName);

    String getDisplayNameById(String id);

    boolean save(User user);

    User getUserInfoByUserId(Long id);
}
