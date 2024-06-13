package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.PlayList_Song;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/13/11:36
 * @Description:
 */
@Repository
public interface PlayList_SongMapper extends Mapper<PlayList_Song> {
    @Select("select song_id from PlayList_Song where playList_id = #{id}")
    String[] selectSongIdListById(Integer id);
}
