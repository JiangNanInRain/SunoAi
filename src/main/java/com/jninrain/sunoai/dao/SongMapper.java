package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.Song;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/16:42
 * @Description:
 */
@Repository
public interface SongMapper extends Mapper<Song> {

    @Select("select * from Song where id = #{id}")
    Song getSongById(String id);

    @Select("select audio_url from Song where id = #{id}")
    String selectAudioUrlById(String id);

    @Update("update Song set play_count = play_count+1 where id = #{id}")
    void updatePlayCountPlus(String id);

//    @Update("update Song set play_count = play_count-1 where id = #{id}")
//    void updatePlayCountSub(String id);

    @Update("update Song set upvote_count = upvote_count+1 where id = #{id}")
    void upvote(String id);

    @Update("update Song set upvote_count = upvote_count+1 where id = #{id}")
    void cancelVote(String id);
}
