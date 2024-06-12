package com.jninrain.sunoai.service;

import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.vo.SongVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/16:44
 * @Description:
 */
public interface SongService {

    void addOneSong(Song song);

    List<Song> getList();

    void deleteOneSong(String song_id);

     Song getSongById(String id);

     String  getPlayAudioUrl(String id);

     void updatePlayCountPlus(String id);
//
//     void updatePlayCountSub(String id);

    void upvote(String id);

    void cancelVote(String id);
}
