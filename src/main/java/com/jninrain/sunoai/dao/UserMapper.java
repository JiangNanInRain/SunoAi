package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/14:55
 * @Description:
 */
@Repository
public interface UserMapper extends Mapper<User> {

    @Select("select  password from User where handle = #{handle}")
    String getPwdByUserName(@Param("handle") String userName);

    @Select("select uid from User where handle = #{userName} ")
    String getIdByUserName(String userName);

    @Select("select display_name from User where uid = #{id}")
    String getDisplayNameById(String id);

    @Select("select  * from User where uid = #{id}")
    User getUserInfoByUserId(Long id);

    @Update("update User set password = #{password} where handle = #{userName} ")
    void updatePwd(@Param("userName") String userName,@Param("password") String password);
}
