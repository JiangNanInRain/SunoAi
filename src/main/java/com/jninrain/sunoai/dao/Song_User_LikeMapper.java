package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.Song_User_Like;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/15:15
 * @Description:
 */
@Repository
public interface Song_User_LikeMapper extends Mapper<Song_User_Like> {

    @Select("select * from Song_User_Like where song_id=#{song_id} AND user_id = #{user_id}")
     Song_User_Like getLike(@Param("song_id") String song_id,@Param("user_id") String user_id);
}
